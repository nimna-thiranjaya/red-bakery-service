package com.redbakery.redbakeryservice.model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;
import java.util.Set;

@Entity
@Data
@Table(name = "product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;

    @Column(name = "product_name", nullable = false, length = 100)
    private String productName;

    @Column(name = "product_description", nullable = true, length = 500)
    private String productDescription;

    @Column(name = "product_price", nullable = false)
    private Double productPrice;

    @Column(name = "product_image", nullable = true)
    private String productImage;

    @Column(name = "is_discounted", nullable = false)
    private Boolean isDiscounted;

    @Column(name = "status", nullable = false)
    private Integer status;

    @ManyToOne
    @JoinColumn(name = "food_type_id", nullable = false)
    private FoodType foodType;

    @Column(name = "added_by", nullable = false)
    private Long addedBy;

    @Column(name = "updated_by", nullable = false)
    private Long updatedBy;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false)
    private Date createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private Date updatedAt;

    @OneToOne
    @JoinColumn(name = "discount_id", nullable = true)
    private Discount discount;

    @OneToMany(mappedBy = "product")
    private Set<WishList> wishLists;

    @OneToMany(mappedBy = "product")
    private Set<CartDetail> cartDetails;
}
