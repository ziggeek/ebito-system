package com.ebito.data_source.model.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "t_clients")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Client {

    @Id
    @Column(name = "t_client_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "t_firstname")
    private String firstname;

    @Column(name = "t_lastname")
    private String lastname;

    @Column(name = "t_middlename")
    private String middlename;

    @OneToOne(mappedBy = "client")
    private Account account;

    @OneToOne(mappedBy = "client")
    private Agreement agreement;
}
