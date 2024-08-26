package com.customersProducts.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "customer")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer customerId;

    @Column
    private String firstName;

    @Column
    private String lastName;

    @Column
    private String emailOffice;

    @Column
    private String emailPersonal;

    @Column
    private String contactNo;

    @Column
    private String familyName;

    @Column
    private String familyContactNo;

    @Column
    private Timestamp createdDate;

    @Column
    private Timestamp lastModifiedDate;
}
