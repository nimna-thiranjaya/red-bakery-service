package com.redbakery.redbakeryservice.model;


import jakarta.persistence.*;

@Entity
@Table(name = "shopping_cart")
public class ShoppingCart {
    @Id
    private int cartId;

    @Column(name = "status")
    private int status;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
}
