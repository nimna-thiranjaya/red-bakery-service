package com.redbakery.redbakeryservice.service.impl;

import com.redbakery.redbakeryservice.common.WellKnownStatus;
import com.redbakery.redbakeryservice.dto.AuthenticationTicketDto;
import com.redbakery.redbakeryservice.dto.request.FoodCategoryRequestDto;
import com.redbakery.redbakeryservice.dto.response.FoodCategoryResponseDto;
import com.redbakery.redbakeryservice.exception.BadRequestException;
import com.redbakery.redbakeryservice.model.FoodCategory;
import com.redbakery.redbakeryservice.repository.FoodCategoryRepository;
import com.redbakery.redbakeryservice.service.AuthenticationService;
import com.redbakery.redbakeryservice.service.FoodCategoryService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FoodCategoryServiceImpl implements FoodCategoryService {

    private final FoodCategoryRepository foodCategoryRepository;

    private final ModelMapper modelMapper;

    @Override
    @Transactional
    public FoodCategoryResponseDto saveFoodCategory(AuthenticationTicketDto authTicket, FoodCategoryRequestDto request) {
        Boolean isCategoryExist = foodCategoryRepository.existsByFoodCategoryNameAndStatusNot(request.getFoodCategoryName(), WellKnownStatus.DELETED.getValue());

        if (isCategoryExist)
            throw new BadRequestException("Food Category Already Exist!");

        FoodCategory foodCategory = new FoodCategory();

        foodCategory = modelMapper.map(request, FoodCategory.class);

        foodCategory.setAddedBy(authTicket.getUserId());
        foodCategory.setStatus(WellKnownStatus.ACTIVE.getValue());

        FoodCategory savedFoodCategory = foodCategoryRepository.save(foodCategory);

        FoodCategoryResponseDto response = modelMapper.map(savedFoodCategory, FoodCategoryResponseDto.class);

        return response;
    }
}
