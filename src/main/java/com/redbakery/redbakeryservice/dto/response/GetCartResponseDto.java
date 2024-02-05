package com.redbakery.redbakeryservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class GetCartResponseDto {
    private Long cartId;

    List<CartResponseDto> cartItems;

    private double totalAmount;

    private double discount;

    private double payableAmount;

    private int totalItems;

}
