package com.redbakery.redbakeryservice.service;

import com.redbakery.redbakeryservice.dto.AuthenticationTicketDto;
import com.redbakery.redbakeryservice.dto.request.FoodCategoryRequestDto;
import com.redbakery.redbakeryservice.dto.response.FoodCategoryResponseDto;

import java.util.List;

public interface FoodCategoryService {
    FoodCategoryResponseDto saveFoodCategory(AuthenticationTicketDto authTicket, FoodCategoryRequestDto request);

    List<FoodCategoryResponseDto> getAllFoodCategory(AuthenticationTicketDto authTicket);

    FoodCategoryResponseDto getFoodCategoryById(AuthenticationTicketDto authTicket, Long id);

    FoodCategoryResponseDto updateFoodCategory(AuthenticationTicketDto authTicket, Long id, FoodCategoryRequestDto request);

    FoodCategoryResponseDto activeInactiveFoodCategory(AuthenticationTicketDto authTicket, Long id, String status);
}
