package ru.yandex.practicum.commerce.payment.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.commerce.api.DeliveryClient;
import ru.yandex.practicum.commerce.api.OrderClient;
import ru.yandex.practicum.commerce.api.ShoppingStoreClient;
import ru.yandex.practicum.commerce.api.dto.OrderDto;
import ru.yandex.practicum.commerce.api.dto.PaymentDto;
import ru.yandex.practicum.commerce.api.dto.ProductDto;
import ru.yandex.practicum.commerce.api.dto.exception.NoPaymentFoundException;
import ru.yandex.practicum.commerce.payment.mapper.PaymentMapper;
import ru.yandex.practicum.commerce.payment.model.Payment;
import ru.yandex.practicum.commerce.payment.model.PaymentState;
import ru.yandex.practicum.commerce.payment.repository.PaymentRepository;
import ru.yandex.practicum.commerce.payment.service.PaymentService;

import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GeneralPaymentService implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final PaymentMapper paymentMapper;
    private final DeliveryClient deliveryClient;

    private final ShoppingStoreClient shoppingStoreClient;
    private final OrderClient orderClient;

    private final double tax = 10;

    @Override
    public PaymentDto create(OrderDto orderDto) {

        Payment payment = Payment.builder()
                .totalPayment(calculateTotalCost(orderDto))
                .deliveryTotal(deliveryClient.calculateDeliveryCost(orderDto))
                .feeTotal(calculateProductCost(orderDto) * (float) tax/100)
                .paymentState(PaymentState.PENDING)
                .build();

        Payment savedPayment = paymentRepository.save(payment);

        return paymentMapper.toPaymentDto(savedPayment);
    }

    @Override
    public float calculateTotalCost(OrderDto orderDto) {
        return (calculateProductCost(orderDto) * (float) (1 + tax/100) +
                        deliveryClient.calculateDeliveryCost(orderDto));
    }

    @Override
    public float calculateProductCost(OrderDto orderDto) {
        Map<UUID, Long> products =  orderDto.products();
        float productCost = 0f;
        for (Map.Entry<UUID, Long> entry : products.entrySet()) {
            ProductDto product = shoppingStoreClient.getProduct(entry.getKey());
            productCost += product.price() * entry.getValue();
        }
        return productCost;
    }

    @Override
    public ResponseEntity<Void> refund(UUID orderId) {
        Payment payment = paymentRepository.findPaymentByOrderId(orderId);
        payment.setPaymentState(PaymentState.SUCCESS);
        paymentRepository.save(payment);
        orderClient.pay(orderId);
        return ResponseEntity.noContent().build();
    }

    @Override
    public void paymentFailed(UUID orderId) {
        Payment payment = paymentRepository.findPaymentByOrderId(orderId);
        payment.setPaymentState(PaymentState.FAILED);
        paymentRepository.save(payment);
        orderClient.abortOrderByPaymentFailed(orderId);
        throw new NoPaymentFoundException(orderId.toString());
    }
}
