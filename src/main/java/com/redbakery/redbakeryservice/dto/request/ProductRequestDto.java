package com.redbakery.redbakeryservice.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ProductRequestDto {
    private Long productId;

    @NotEmpty(message = "Product Name is required")
    private String productName;

    private String productDescription;

    @NotEmpty(message = "Product Price is required")
    private Double productPrice;

    @NotEmpty(message = "Product Image is required")
    private String productImage;

    @NotNull(message = "Food Type Id is required")
    private Integer foodTypeId;
}
