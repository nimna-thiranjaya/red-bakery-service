package com.redbakery.redbakeryservice.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Data
@Table(name = "discount")
public class Discount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long discountId;

    @Column(name = "discount_percentage", nullable = false)
    private Double discountPercentage;

    @Column(name = "new_price", nullable = false)
    private Double newPrice;

    @Column(name = "discount_price", nullable = false)
    private Double discountPrice;

    @Column(name = "added_by", nullable = false)
    private Long addedBy;

    @Column(name = "status", nullable = false)
    private Integer status;

    @Column(name="start_date", nullable = false)
    private Date startDate;

    @Column(name="end_date", nullable = false)
    private Date endDate;
}
