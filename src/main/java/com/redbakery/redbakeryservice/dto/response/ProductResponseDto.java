package com.redbakery.redbakeryservice.dto.response;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ProductResponseDto {
    private Long productId;
    private String productName;
    private String productDescription;
    private Double productPrice;
    private String productImage;
    private Long foodTypeId;
    private String foodTypeName;
    private Boolean isDiscounted;
    private Integer status;
    private Double discountPercentage;
    private Date startDate;
    private Date endDate;
}
