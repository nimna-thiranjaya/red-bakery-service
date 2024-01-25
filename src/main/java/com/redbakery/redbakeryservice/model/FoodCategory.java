package com.redbakery.redbakeryservice.model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Set;

@Entity
@Table(name = "food_category")
@Data
public class FoodCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long foodCategoryId;

    @Column(name = "food_category_name", nullable = false)
    private String foodCategoryName;

    @Column(name = "food_category_description", nullable = true)
    private String foodCategoryDescription;

    @Column(name = "added_by", nullable = false)
    private Long addedBy;

    @OneToMany(mappedBy="foodCategory")
    private Set<FoodType> foodTypes;

    @Column(name = "status", nullable = false)
    private Integer status;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false)
    private java.sql.Timestamp createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private java.sql.Timestamp updatedAt;

}
