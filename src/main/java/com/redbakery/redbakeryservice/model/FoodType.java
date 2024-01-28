package com.redbakery.redbakeryservice.model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;

@Entity
@Table(name = "food_type")
@Data
public class FoodType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long foodTypeId;

    @Column(name = "food_type_name", nullable = false)
    private String foodTypeName;

    @Column(name = "food_type_description", nullable = true)
    private String foodTypeDescription;

    @Column(name = "added_by", nullable = false)
    private Long addedBy;

    @Column(name = "updated_by", nullable = false)
    private Long updatedBy;

    @ManyToOne
    @JoinColumn(name = "food_category_id", nullable = false)
    private FoodCategory foodCategory;

    @Column(name = "status", nullable = false)
    private Integer status;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false)
    private Date createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private Date updatedAt;

}
