package com.redbakery.redbakeryservice.model;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;

@Entity
@Table(name = "payment")
public class Payment {
    @Id
    private int paymentId;

    @Column(name = "payment_type")
    private int paymentType;

    @Column(name = "payment_status")
    private String paymentStatus;

    @Column(name = "payment_date")
    private String paymentDate;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false)
    private Date createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private Date updatedAt;

    @OneToOne
    private Order order;
}
