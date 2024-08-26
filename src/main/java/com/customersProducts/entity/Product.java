package com.customersProducts.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.math.BigDecimal;

import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer bookId;

    @Column
    private String bookTitle;

    @Column
    private BigDecimal bookPrice;

    @Column
    private Integer bookQuantity;

    @Column
    private Timestamp createdDate;

    @Column
    private Timestamp lastModifiedDate;
}
