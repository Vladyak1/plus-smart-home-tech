package ru.yandex.practicum.commerce.payment.service;


import org.springframework.http.ResponseEntity;
import ru.yandex.practicum.commerce.api.dto.OrderDto;
import ru.yandex.practicum.commerce.api.dto.PaymentDto;

import java.util.UUID;

public interface PaymentService {

    PaymentDto create(OrderDto orderDto);

    float calculateTotalCost(OrderDto orderDto);

    float calculateProductCost(OrderDto orderDto);

    ResponseEntity<Void> refund(UUID orderId);

    void paymentFailed(UUID orderId);

}
