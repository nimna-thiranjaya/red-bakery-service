package com.redbakery.redbakeryservice.dto.request;

import com.redbakery.redbakeryservice.model.FoodCategory;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class FoodTypeRequestDto {
    private Long foodTypeId;

    @NotEmpty(message = "Food Type Name is required")
    private String foodTypeName;

    @Max(value = 500, message = "Description should be less than 500 characters")
    private String foodTypeDescription;

    @NotEmpty(message = "Food Category is required")
    private FoodCategory foodCategory;
}
