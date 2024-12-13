package ru.yandex.practicum.commerce.order.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.yandex.practicum.commerce.api.DeliveryClient;
import ru.yandex.practicum.commerce.api.PaymentClient;
import ru.yandex.practicum.commerce.api.WarehouseClient;
import ru.yandex.practicum.commerce.api.dto.*;
import ru.yandex.practicum.commerce.api.dto.enums.DeliveryState;
import ru.yandex.practicum.commerce.api.dto.enums.OrderState;
import ru.yandex.practicum.commerce.api.dto.exception.NoOrderFoundException;
import ru.yandex.practicum.commerce.api.dto.exception.NotAuthorizedUserException;
import ru.yandex.practicum.commerce.api.dto.exception.ProductInShoppingCartLowQuantityInWarehouse;
import ru.yandex.practicum.commerce.api.dto.exception.ProductNotFoundException;
import ru.yandex.practicum.commerce.order.mapper.AddressMapper;
import ru.yandex.practicum.commerce.order.mapper.OrderMapper;
import ru.yandex.practicum.commerce.order.model.Order;
import ru.yandex.practicum.commerce.order.repository.OrderRepository;
import ru.yandex.practicum.commerce.order.service.OrderService;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GeneralOrderService implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final AddressMapper addressMapper;

    private final WarehouseClient warehouseClient;
    private final DeliveryClient deliveryClient;
    private final PaymentClient paymentClient;

    @Override
    public OrderDto get(String username) {
        Order receivedOrder = orderRepository.findByUsername(username).orElseThrow(
                () -> new NotAuthorizedUserException("Order with with username: " + username + ", not found"));
        return orderMapper.toOrderDto(receivedOrder);
    }

    @Override
    @Transactional
    public OrderDto create(CreateNewOrderRequest createNewOrderRequest) {

        BookedProductsDto productsCheckAssembly =
                warehouseClient.checkForProductsSufficiency(createNewOrderRequest.shoppingCart());

        Order creatingOrder = Order.builder()
                .shoppingCartId(createNewOrderRequest.shoppingCart().shoppingCartId())
                .state(OrderState.NEW)
                .products(createNewOrderRequest.shoppingCart().products())
                .deliveryVolume(productsCheckAssembly.deliveryVolume())
                .deliveryWeight(productsCheckAssembly.deliveryWeight())
                .fragile(productsCheckAssembly.fragile())
                .address(
                        addressMapper.toAddress(
                                createNewOrderRequest.deliveryAddress()))
                .build();

        Order intermediateCreatedOrder = orderRepository.save(creatingOrder);

        DeliveryDto deliveryDto = deliveryClient.create(
                DeliveryDto.builder()
                        .fromAddress(warehouseClient.getWarehouseAddress())
                        .toAddress(createNewOrderRequest.deliveryAddress())
                        .orderId(intermediateCreatedOrder.getOrderId())
                        .deliveryState(DeliveryState.CREATED)
                        .build());

        intermediateCreatedOrder.setDeliveryId(deliveryDto.orderId());

        PaymentDto createdPayment = paymentClient.create(orderMapper.toOrderDto(intermediateCreatedOrder));

        intermediateCreatedOrder.setPaymentId(createdPayment.paymentId());
        intermediateCreatedOrder.setTotalPrice(createdPayment.totalPayment());
        intermediateCreatedOrder.setDeliveryPrice(createdPayment.deliveryTotal());
        intermediateCreatedOrder.setProductPrice(
                paymentClient.calculateProductCost(orderMapper.toOrderDto(intermediateCreatedOrder)));

        Order savedOrder = orderRepository.save(intermediateCreatedOrder);

        return orderMapper.toOrderDto(savedOrder);
    }

    @Override
    @Transactional
    public OrderDto returnOrder(ProductReturnRequest productReturnRequest) {

        Order receivedOrder = orderRepository.findById(productReturnRequest.orderId()).orElseThrow(
                () -> new NoOrderFoundException("Order with id " + productReturnRequest.orderId() + " not found"));

        warehouseClient.returnProducts(productReturnRequest.products());

        receivedOrder.setState(OrderState.PRODUCT_RETURNED);

        return orderMapper.toOrderDto(
                orderRepository.save(receivedOrder));
    }

    @Override
    public OrderDto pay(UUID orderId) {

        Order receivedOrder = orderRepository.findById(orderId).orElseThrow(
                () -> new NoOrderFoundException("Order with id " + orderId + " not found"));

        paymentClient.refund(orderId);

        receivedOrder.setState(OrderState.PAID);

        return orderMapper.toOrderDto(orderRepository.save(receivedOrder));
    }

    @Override
    public OrderDto abortByPayment(UUID orderId) {

        Order receivedOrder = orderRepository.findById(orderId).orElseThrow(
                () -> new NoOrderFoundException("Order with id " + orderId + " not found"));

        paymentClient.paymentFailed(orderId);

        receivedOrder.setState(OrderState.PAYMENT_FAILED);

        return orderMapper.toOrderDto(orderRepository.save(receivedOrder));
    }

    @Override
    public OrderDto assembly(UUID orderId) {
        Order receivedOrder = orderRepository.findById(orderId).orElseThrow(
                () -> new NoOrderFoundException("Order with id " + orderId + " not found"));

        try {
            warehouseClient.assemblyProductsForOrder(
                    AssemblyProductForOrderRequest.builder()
                            .orderId(orderId)
                            .products(receivedOrder.getProducts())
                            .build());

            receivedOrder.setState(OrderState.ASSEMBLED);
        } catch (ProductNotFoundException | ProductInShoppingCartLowQuantityInWarehouse e) {
            abortAssemblyByFail(orderId);
        }

        return orderMapper.toOrderDto(orderRepository.save(receivedOrder));

    }

    @Override
    public OrderDto abortAssemblyByFail(UUID orderId) {
        Order receivedOrder = orderRepository.findById(orderId).orElseThrow(
                () -> new NoOrderFoundException("Order with id " + orderId + " not found"));
        receivedOrder.setState(OrderState.ASSEMBLED_FAILED);

        return orderMapper.toOrderDto(orderRepository.save(receivedOrder));
    }

    @Override
    public OrderDto deliver(UUID orderId) {

        Order receivedOrder = orderRepository.findById(orderId).orElseThrow(
                () -> new NoOrderFoundException("Order with id " + orderId + " not found"));

        deliveryClient.setDeliveryStatusSuccessful(orderId);

        receivedOrder.setState(OrderState.DELIVERED);

        return orderMapper.toOrderDto(orderRepository.save(receivedOrder));

    }

    @Override
    public OrderDto abortByFail(UUID orderId) {

        Order receivedOrder = orderRepository.findById(orderId).orElseThrow(
                () -> new NoOrderFoundException("Order with id " + orderId + " not found"));

        deliveryClient.setFailedStatusToDelivery(orderId);

        receivedOrder.setState(OrderState.DELIVERY_FAILED);

        return orderMapper.toOrderDto(orderRepository.save(receivedOrder));
    }

    @Override
    public OrderDto complete(UUID orderId) {

        Order receivedOrder = orderRepository.findById(orderId).orElseThrow(
                () -> new NoOrderFoundException("Order with id " + orderId + " not found"));

        receivedOrder.setState(OrderState.COMPLETED);

        return orderMapper.toOrderDto(orderRepository.save(receivedOrder));

    }

    @Override
    public OrderDto calculateOrderCost(UUID orderId) {
        Order receivedOrder = orderRepository.findById(orderId).orElseThrow(
                () -> new NoOrderFoundException("Order with id " + orderId + " not found"));

        if (receivedOrder.getTotalPrice() == null) {
            receivedOrder.setTotalPrice(paymentClient.calculateTotalCost(
                    orderMapper.toOrderDto(receivedOrder)));
            orderRepository.save(receivedOrder);
        }
        return orderMapper.toOrderDto(receivedOrder);
    }

    @Override
    public OrderDto calculateDeliveryCost(UUID orderId) {
        Order receivedOrder = orderRepository.findById(orderId).orElseThrow(
                () -> new NoOrderFoundException("Order with id " + orderId + " not found"));

        if (receivedOrder.getDeliveryPrice() == null) {
            receivedOrder.setDeliveryPrice(deliveryClient.calculateDeliveryCost(
                    orderMapper.toOrderDto(receivedOrder)));
            orderRepository.save(receivedOrder);
        }

        return orderMapper.toOrderDto(receivedOrder);
    }

}
