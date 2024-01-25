package com.redbakery.redbakeryservice.service;

import com.redbakery.redbakeryservice.dto.AuthenticationTicketDto;
import com.redbakery.redbakeryservice.dto.request.FoodCategoryRequestDto;
import com.redbakery.redbakeryservice.dto.response.FoodCategoryResponseDto;

public interface FoodCategoryService {
    FoodCategoryResponseDto saveFoodCategory(AuthenticationTicketDto authTicket, FoodCategoryRequestDto request);
}
