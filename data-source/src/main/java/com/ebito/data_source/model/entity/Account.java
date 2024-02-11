package com.ebito.data_source.model.entity;

import com.ebito.currency.Currency;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "t_accounts")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Account {

    @Id
    @Column(name = "t_account_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "t_client_id")
    @JsonIgnore
    private Client client;

    @Column(name = "t_account_number")
    private Integer accountNumber;

    @Column(name = "t_account_currency")
    @Enumerated(EnumType.STRING)
    private Currency accountCurrency;

    @OneToMany(mappedBy = "account")
    private List<Operation> operations;
}
