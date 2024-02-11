package com.ebito.data_source.model.entity;

import com.ebito.payment.PaymentMethod;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "t_operations")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class Operation {

    @Id
    @Column(name = "t_operation_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "t_timestamp")
    private LocalDateTime timestamp;

    @Column(name = "t_payment_method")
    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;

    @Column(name = "t_sum")
    private Long sum;

    @ManyToOne
    @JoinColumn(name = "t_account_id")
    private Account account;
}
