package ru.yandex.practicum.commerce.delivery;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.commerce.api.dto.DeliveryDto;
import ru.yandex.practicum.commerce.api.dto.OrderDto;
import ru.yandex.practicum.commerce.delivery.service.DeliveryService;

import java.util.UUID;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/delivery")
public class DeliveryController {

    private final DeliveryService deliveryService;

    @PutMapping
    public DeliveryDto create(DeliveryDto deliveryDto) {
        log.info("Creating delivery: {}", deliveryDto);
        DeliveryDto savedDeliveryDto = deliveryService.create(deliveryDto);
        log.info("Created delivery: {}", savedDeliveryDto);
        return savedDeliveryDto;
    }

    @PostMapping("/successful")
    public void setDeliveryStatusSuccessful(UUID orderId) {
        log.info("setting successful to delivery: {}", orderId);
        deliveryService.setSuccessfulToDelivery(orderId);
        log.info("Set successful to delivery: {} completed", orderId);
    }

    @PostMapping("/picked")
    public void setDeliveryStatusPicked(UUID orderId) {
        log.info("setting picked to delivery: {}", orderId);
        deliveryService.setPickedToDelivery(orderId);
        log.info("Set picked to delivery: {} completed", orderId);
    }

    @PostMapping("/failed")
    public void setFailedStatusToDelivery(UUID orderId) {
        log.info("setting failed to delivery: {}", orderId);
        deliveryService.setFailedToDelivery(orderId);
        log.info("Set failed to delivery: {} completed", orderId);
    }

    @PostMapping("/cost")
    public Float calculateDeliveryCost(OrderDto orderDto) {
        log.info("Calculating deliveryCost of order: {}", orderDto);
        Float calculatedDeliveryCost = deliveryService.calculateDeliveryCost(orderDto);
        log.info("Calculated deliveryCost: {}", calculatedDeliveryCost);
        return calculatedDeliveryCost;
    }

}
