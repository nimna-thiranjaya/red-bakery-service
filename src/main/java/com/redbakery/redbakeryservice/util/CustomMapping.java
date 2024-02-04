package com.redbakery.redbakeryservice.util;

import com.redbakery.redbakeryservice.dto.response.ProductResponseDto;
import com.redbakery.redbakeryservice.dto.response.WishListResponseDto;
import com.redbakery.redbakeryservice.model.Product;
import com.redbakery.redbakeryservice.model.Role;

import java.util.Date;

public class CustomMapping {
    public static ProductResponseDto mapProductToProductResponseDto(Product product, String role) {
        Date currentDate = new Date();
        ProductResponseDto productResponseDto = new ProductResponseDto();

        if (role.equalsIgnoreCase(Role.ADMIN.name())) {
            productResponseDto.setProductId(product.getProductId());
            productResponseDto.setProductName(product.getProductName());
            productResponseDto.setProductDescription(product.getProductDescription());
            productResponseDto.setProductImage(product.getProductImage());
            productResponseDto.setFoodTypeId(product.getFoodType().getFoodTypeId());
            productResponseDto.setFoodTypeName(product.getFoodType().getFoodTypeName());
            productResponseDto.setStatus(product.getStatus());
            productResponseDto.setIsDiscounted(product.getIsDiscounted());
            if (product.getIsDiscounted()) {
                productResponseDto.setDiscountPercentage(product.getDiscount().getDiscountPercentage());
                productResponseDto.setProductPrice(product.getProductPrice());
                productResponseDto.setStartDate(product.getDiscount().getStartDate());
                productResponseDto.setEndDate(product.getDiscount().getEndDate());
                productResponseDto.setNewPrice(product.getDiscount().getNewPrice());
                productResponseDto.setDiscountPrice(product.getDiscount().getDiscountPrice());
            } else {
                productResponseDto.setProductPrice(product.getProductPrice());
                productResponseDto.setDiscountPercentage(null);
                productResponseDto.setNewPrice(null);
                productResponseDto.setDiscountPrice(null);
                productResponseDto.setStartDate(null);
                productResponseDto.setEndDate(null);
            }
        } else {
            productResponseDto.setProductId(product.getProductId());
            productResponseDto.setProductName(product.getProductName());
            productResponseDto.setProductDescription(product.getProductDescription());
            productResponseDto.setProductImage(product.getProductImage());
            productResponseDto.setFoodTypeId(product.getFoodType().getFoodTypeId());
            productResponseDto.setFoodTypeName(product.getFoodType().getFoodTypeName());
            productResponseDto.setStatus(product.getStatus());
            productResponseDto.setIsDiscounted(false);
            if (product.getIsDiscounted()) {
                if (currentDate.after(product.getDiscount().getStartDate()) && currentDate.before(product.getDiscount().getEndDate())) {
                    productResponseDto.setIsDiscounted(product.getIsDiscounted());
                    productResponseDto.setDiscountPercentage(product.getDiscount().getDiscountPercentage());
                    productResponseDto.setProductPrice(product.getProductPrice());
                    productResponseDto.setStartDate(product.getDiscount().getStartDate());
                    productResponseDto.setEndDate(product.getDiscount().getEndDate());
                    productResponseDto.setNewPrice(product.getDiscount().getNewPrice());
                    productResponseDto.setDiscountPrice(product.getDiscount().getDiscountPrice());
                } else {
                    productResponseDto.setProductPrice(product.getProductPrice());
                    productResponseDto.setDiscountPercentage(null);
                    productResponseDto.setNewPrice(null);
                    productResponseDto.setDiscountPrice(null);
                    productResponseDto.setStartDate(null);
                    productResponseDto.setEndDate(null);
                }
            } else {
                productResponseDto.setProductPrice(product.getProductPrice());
                productResponseDto.setDiscountPercentage(null);
                productResponseDto.setNewPrice(null);
                productResponseDto.setDiscountPrice(null);
                productResponseDto.setStartDate(null);
                productResponseDto.setEndDate(null);
            }
        }

        return productResponseDto;
    }

    public static WishListResponseDto mapProductToWishListResponseDto(Product product){
        Date currentDate = new Date();
        WishListResponseDto wishListResponseDto = new WishListResponseDto();

        wishListResponseDto.setProductId(product.getProductId());
        wishListResponseDto.setProductName(product.getProductName());
        wishListResponseDto.setProductDescription(product.getProductDescription());
        wishListResponseDto.setProductImage(product.getProductImage());
        wishListResponseDto.setFoodTypeId(product.getFoodType().getFoodTypeId());
        wishListResponseDto.setFoodTypeName(product.getFoodType().getFoodTypeName());
        wishListResponseDto.setStatus(product.getStatus());
        wishListResponseDto.setIsDiscounted(false);

        if (product.getIsDiscounted()) {
            if (currentDate.after(product.getDiscount().getStartDate()) && currentDate.before(product.getDiscount().getEndDate())) {
                wishListResponseDto.setIsDiscounted(product.getIsDiscounted());
                wishListResponseDto.setDiscountPercentage(product.getDiscount().getDiscountPercentage());
                wishListResponseDto.setProductPrice(product.getProductPrice());
                wishListResponseDto.setStartDate(product.getDiscount().getStartDate());
                wishListResponseDto.setEndDate(product.getDiscount().getEndDate());
                wishListResponseDto.setNewPrice(product.getDiscount().getNewPrice());
                wishListResponseDto.setDiscountPrice(product.getDiscount().getDiscountPrice());
            } else {
                wishListResponseDto.setProductPrice(product.getProductPrice());
                wishListResponseDto.setDiscountPercentage(null);
                wishListResponseDto.setNewPrice(null);
                wishListResponseDto.setDiscountPrice(null);
                wishListResponseDto.setStartDate(null);
                wishListResponseDto.setEndDate(null);
            }
        } else {
            wishListResponseDto.setProductPrice(product.getProductPrice());
            wishListResponseDto.setDiscountPercentage(null);
            wishListResponseDto.setNewPrice(null);
            wishListResponseDto.setDiscountPrice(null);
            wishListResponseDto.setStartDate(null);
            wishListResponseDto.setEndDate(null);
        }


        return wishListResponseDto;
    }

}
