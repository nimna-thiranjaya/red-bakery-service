package com.redbakery.redbakeryservice.service;

import com.redbakery.redbakeryservice.dto.AuthenticationTicketDto;
import com.redbakery.redbakeryservice.dto.request.FoodTypeRequestDto;
import com.redbakery.redbakeryservice.dto.response.FoodTypeResponseDto;

import java.util.List;

public interface FoodTypeService {
    FoodTypeResponseDto saveFoodType(AuthenticationTicketDto authTicket, FoodTypeRequestDto request);

    List<FoodTypeResponseDto> getAllFoodType(AuthenticationTicketDto authTicket);

    FoodTypeResponseDto getFoodTypeById(AuthenticationTicketDto authTicket, Long id);

    FoodTypeResponseDto updateFoodType(AuthenticationTicketDto authTicket, Long id, FoodTypeRequestDto request);

    FoodTypeResponseDto activeInactiveFoodType(AuthenticationTicketDto authTicket, Long id, String status);

    FoodTypeResponseDto deleteFoodType(AuthenticationTicketDto authTicket, Long id);

    List<FoodTypeResponseDto> getFoodTypeByCategory(AuthenticationTicketDto authTicket, Long id);
}
