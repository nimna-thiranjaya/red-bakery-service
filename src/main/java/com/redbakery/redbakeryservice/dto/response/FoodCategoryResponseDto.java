package com.redbakery.redbakeryservice.dto.response;

import com.redbakery.redbakeryservice.model.FoodType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class FoodCategoryResponseDto {
    private Long foodCategoryId;

    private String foodCategoryName;

    private String foodCategoryDescription;

    private Integer status;

    private Date createdAt;

    private Date updatedAt;
}
