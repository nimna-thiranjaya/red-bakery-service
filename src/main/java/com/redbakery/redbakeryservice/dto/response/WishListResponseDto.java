package com.redbakery.redbakeryservice.dto.response;

import java.util.Date;

public class WishListResponseDto {
    private Long productId;
    private String productName;
    private String productDescription;
    private Double productPrice;
    private String productImage;
    private Long foodTypeId;
    private String foodTypeName;
    private Boolean isDiscounted;
    private Double newPrice;
    private Double discountPrice;
    private Integer status;
    private Double discountPercentage;
    private Date startDate;
    private Date endDate;
    private Date addedDate;
}
