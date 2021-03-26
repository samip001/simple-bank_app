package com.samip.bank.test.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "interest_rate")
public class InterestRate {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "bank_name",nullable = false)
    private String bankName;

    @Column(name = "interest_rate",nullable = false)
    private float interest_rate;

    @Column(name = "terms",nullable = false)
    private String terms;

    @Column(name = "details",nullable = false)
    private String details;
}

