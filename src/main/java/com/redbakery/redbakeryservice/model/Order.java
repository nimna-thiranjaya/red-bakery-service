package com.redbakery.redbakeryservice.model;

import jakarta.persistence.*;

@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int orderId;

    @Column(name = "order_date")
    private String orderDate;

    @Column(name = "order_status")
    private String orderStatus;

    @Column(name = "total_price")
    private double totalPrice;

    @Column(name = "discount")
    private double discount;

    @Column(name = "address_line1")
    private String addressLine1;

    @Column(name = "address_line2")
    private String addressLine2;

    @Column(name = "city")
    private String city;

    @Column(name = "zip")
    private String zip;
}
