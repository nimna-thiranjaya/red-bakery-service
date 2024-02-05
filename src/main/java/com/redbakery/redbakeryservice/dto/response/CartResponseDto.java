package com.redbakery.redbakeryservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CartResponseDto {
    private Long cartId;
    private Long productId;
    private Long cartDetailId;
    private String productName;
    private int quantity;
    private double unitPrice;
    private double discountPercentage;
    private double discountPrice;
    private double totalPrice;
}
