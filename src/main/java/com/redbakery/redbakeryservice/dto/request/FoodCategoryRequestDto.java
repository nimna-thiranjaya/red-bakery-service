package com.redbakery.redbakeryservice.dto.request;

import com.redbakery.redbakeryservice.model.FoodType;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class FoodCategoryRequestDto {
    private Long foodCategoryId;

    @NotEmpty(message = "Food Category Name is required")
    private String foodCategoryName;

    @Max(value = 500, message = "Description should not be more than 500 characters")
    private String foodCategoryDescription;
}
