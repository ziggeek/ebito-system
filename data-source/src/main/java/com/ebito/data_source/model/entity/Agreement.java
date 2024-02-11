package com.ebito.data_source.model.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "t_agreements")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Agreement {

    @Id
    @Column(name = "t_agreement_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "t_agreement_number")
    private Integer agreementNumber;

    @Column(name = "t_agreement_date")
    private LocalDate agreementDate;

    @OneToOne
    @JoinColumn(name = "t_client_id")
    private Client client;
}
