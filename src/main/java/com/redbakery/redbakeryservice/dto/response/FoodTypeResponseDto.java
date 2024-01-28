package com.redbakery.redbakeryservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;


import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class FoodTypeResponseDto {
    private Long foodTypeId;

    private String foodTypeName;

    private String foodTypeDescription;

    private Long foodCategoryId;

    private String foodCategoryName;

    private Integer status;

    private Date createdAt;

    private Date updatedAt;
}
