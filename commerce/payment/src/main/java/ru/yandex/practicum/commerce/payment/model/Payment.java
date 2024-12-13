package ru.yandex.practicum.commerce.payment.model;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;


@Entity
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "paymentId")
@Table(name = "payments")
public class Payment {

    @Id
    @UuidGenerator
    private UUID paymentId;

    @Column(name = "order_id")
    private UUID orderId;

    @Column(name = "total_payment")
    private Float totalPayment;

    @Column(name = "delivery_total")
    private Float deliveryTotal;

    @Column(name = "fee_total")
    private Float feeTotal;

    @Column(name = "payment_state")
    private PaymentState paymentState;

}