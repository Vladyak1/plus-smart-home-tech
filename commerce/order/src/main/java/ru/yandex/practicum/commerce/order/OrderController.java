package ru.yandex.practicum.commerce.order;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.commerce.api.dto.CreateNewOrderRequest;
import ru.yandex.practicum.commerce.api.dto.OrderDto;
import ru.yandex.practicum.commerce.api.dto.ProductReturnRequest;
import ru.yandex.practicum.commerce.order.service.OrderService;

import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api/v1/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @GetMapping
    public OrderDto get(@RequestParam String username) {
        log.info("Get Order by username {}", username);
        OrderDto returnedDto = orderService.get(username);
        log.info("Returning Order by username {}", username);
        return returnedDto;
    }

    @PutMapping
    public OrderDto create(@RequestBody CreateNewOrderRequest createNewOrderRequest) {
        log.info("Creating new order {}", createNewOrderRequest);
        OrderDto createdDto = orderService.create(createNewOrderRequest);
        log.info("Created new order {}", createdDto);
        return createdDto;
    }

    @PostMapping("/return")
    public OrderDto returnOrder(ProductReturnRequest productReturnRequest) {
        log.info("Returning order {}", productReturnRequest);
        OrderDto orderDto = orderService.returnOrder(productReturnRequest);
        log.info("Returned order {}", orderDto);
        return orderDto;
    }

    @PostMapping("/payment")
    public OrderDto pay(@RequestBody UUID orderId) {
        log.info("Paying order {}", orderId);
        OrderDto orderDto = orderService.pay(orderId);
        log.info("Payed order {}", orderDto);
        return orderDto;
    }

    @PostMapping("/payment/failed")
    public OrderDto abortOrderByPaymentFailed(@RequestBody UUID orderId) {
        log.info("Aborting order with id: {} by payment failed", orderId);
        OrderDto orderDto = orderService.abortByPayment(orderId);
        log.info("Aborted order with id: {} by payment failed", orderDto);
        return orderDto;
    }

    @PostMapping("/delivery")
    public OrderDto deliver(@RequestBody UUID orderId) {
        log.info("Delivering order by id {}", orderId);
        OrderDto orderDto = orderService.deliver(orderId);
        log.info("Delivered order by id {}", orderDto);
        return orderDto;
    }

    @PostMapping("/delivery/failed")
    public OrderDto abortDeliverByFail(@RequestBody UUID orderId) {
        log.info("Aborting order with id {} by fail", orderId);
        OrderDto orderDto = orderService.abortByFail(orderId);
        log.info("Aborted order with id {} by fail", orderDto);
        return orderDto;
    }

    @PostMapping("/completed")
    public OrderDto complete(@RequestBody UUID orderId) {
        log.info("Completing order by id: {}", orderId);
        OrderDto completedOrder = orderService.complete(orderId);
        log.info("Completed order by id: {}", completedOrder);
        return completedOrder;
    }

    @PostMapping("/total")
    public OrderDto calculateOrderCost(@RequestBody UUID orderId) {
        log.info("Calculating price for order with id: {}", orderId);
        OrderDto orderDto = orderService.calculateOrderCost(orderId);
        log.info("Calculated price for order with id: {}", orderDto);
        return orderDto;
    }

    @PostMapping("/calculate/delivery")
    public OrderDto calculateDeliveryCost(@RequestBody UUID orderId) {
        log.info("Calculating delivery cost for order with id: {}", orderId);
        OrderDto orderDto = orderService.calculateDeliveryCost(orderId);
        log.info("Calculated delivery cost for order with id: {}", orderDto);
        return orderDto;
    }

    @PostMapping("/assembly")
    public OrderDto assembly(@RequestBody UUID orderId) {
        log.info("Assembling order with id: {}", orderId);
        OrderDto orderDto = orderService.assembly(orderId);
        log.info("Assembled order with id: {}", orderDto);
        return orderDto;
    }

    @PostMapping("/assembly/failed")
    public OrderDto abortAssemblyByFail(@RequestBody UUID orderId) {
        log.info("Aborting order with id: {}", orderId);
        OrderDto orderDto = orderService.abortAssemblyByFail(orderId);
        log.info("Aborted order with id: {}", orderDto);
        return orderDto;
    }
}
