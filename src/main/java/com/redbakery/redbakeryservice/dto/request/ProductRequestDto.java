package com.redbakery.redbakeryservice.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ProductRequestDto {
    private Long productId;

    @NotEmpty(message = "Product Name is required")
    private String productName;

    private String productDescription;

    @NotNull(message = "Product Price is required")
    private Double productPrice;

    @NotEmpty(message = "Product Image is required")
    private String productImage;

    @NotNull(message = "Food Type is required")
    private Long foodTypeId;

    private Boolean isDiscounted;

    private Double discountPercentage;

    private Double newPrice;

    private Date startDate;

    private Date endDate;
}
