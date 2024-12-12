package ru.yandex.practicum.commerce.delivery.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.yandex.practicum.commerce.api.OrderClient;
import ru.yandex.practicum.commerce.api.WarehouseClient;
import ru.yandex.practicum.commerce.api.dto.DeliveryDto;
import ru.yandex.practicum.commerce.api.dto.OrderDto;
import ru.yandex.practicum.commerce.api.dto.ShippedToDeliveryRequest;
import ru.yandex.practicum.commerce.api.dto.enums.DeliveryState;
import ru.yandex.practicum.commerce.delivery.mapper.DeliveryMapper;
import ru.yandex.practicum.commerce.delivery.model.Address;
import ru.yandex.practicum.commerce.delivery.model.Delivery;
import ru.yandex.practicum.commerce.delivery.repository.DeliveryRepository;
import ru.yandex.practicum.commerce.delivery.service.DeliveryService;

import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class GeneralDeliveryService implements DeliveryService {

    private final DeliveryRepository deliveryRepository;
    private final DeliveryMapper deliveryMapper;

    private final OrderClient orderClient;
    private final WarehouseClient warehouseClient;

    @Value("${delivery.baseCost}")
    private float baseCost;

    @Override
    @Transactional
    public DeliveryDto create(DeliveryDto deliveryDto) {
        Delivery savedDelivery = deliveryRepository.save(deliveryMapper.toDelivery(deliveryDto));
        return deliveryMapper.toDeliveryDto(savedDelivery);
    }

    @Override
    public void setSuccessfulToDelivery(UUID orderId) {
        Delivery currentDelivery = deliveryRepository.findByOrderId(orderId);
        currentDelivery.setDeliveryState(DeliveryState.DELIVERED);
        deliveryRepository.save(currentDelivery);
        orderClient.deliver(orderId);
        log.info("Successfully delivered. {}", orderId);
    }

    @Override
    @Transactional
    public void setPickedToDelivery(UUID orderId) {
        Delivery currentDelivery = deliveryRepository.findByOrderId(orderId);
        currentDelivery.setDeliveryState(DeliveryState.IN_PROGRESS);
        warehouseClient.shipToDelivery(
                new ShippedToDeliveryRequest(currentDelivery.getOrderId(),
                        currentDelivery.getDeliveryId()));
        deliveryRepository.save(currentDelivery);
        log.info("Successfully picked. {}", orderId);
    }

    @Override
    @Transactional
    public void setFailedToDelivery(UUID orderId) {
        Delivery currentDelivery = deliveryRepository.findByOrderId(orderId);
        currentDelivery.setDeliveryState(DeliveryState.FAILED);
        deliveryRepository.save(currentDelivery);
        orderClient.abortDeliverByFail(orderId);
        log.info("Successfully failed. {}", orderId);
    }

    @Override
    public float calculateDeliveryCost(OrderDto orderDto) {
        float deliveryCost = baseCost;

        Delivery delivery = deliveryRepository.findByOrderId(orderDto.orderId());
        Address addressFrom = delivery.getFromAddress();
        Address addressTo = delivery.getToAddress();

        if(addressFrom.toString().contains("ADDRESS_1")) {
            deliveryCost = deliveryCost * 1;
        } else if(addressFrom.toString().contains("ADDRESS_2")) {
            deliveryCost = deliveryCost * 2;
        }

        if(orderDto.fragile()) {
            deliveryCost = deliveryCost * 1.2f;
        }

        deliveryCost = deliveryCost * (float) (1 + orderDto.deliveryWeight()*0.3f);

        deliveryCost = deliveryCost * (float) (1 + orderDto.deliveryVolume()*0.2f);

        if(!addressTo.toString().contains("ADDRESS_1") && !addressTo.toString().contains("ADDRESS_2")) {
            deliveryCost = deliveryCost * 1.2f;
        }

        return deliveryCost;

    }
}
