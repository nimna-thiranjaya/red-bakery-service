package com.redbakery.redbakeryservice.service.impl;

import com.redbakery.redbakeryservice.common.WellKnownStatus;
import com.redbakery.redbakeryservice.dto.AuthenticationTicketDto;
import com.redbakery.redbakeryservice.dto.request.FoodCategoryRequestDto;
import com.redbakery.redbakeryservice.dto.response.FoodCategoryResponseDto;
import com.redbakery.redbakeryservice.exception.BadRequestException;
import com.redbakery.redbakeryservice.exception.NotFoundException;
import com.redbakery.redbakeryservice.model.FoodCategory;
import com.redbakery.redbakeryservice.model.Role;
import com.redbakery.redbakeryservice.repository.FoodCategoryRepository;
import com.redbakery.redbakeryservice.service.AuthenticationService;
import com.redbakery.redbakeryservice.service.FoodCategoryService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;

import java.util.List;

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
        foodCategory.setUpdatedBy(authTicket.getUserId());
        foodCategory.setStatus(WellKnownStatus.ACTIVE.getValue());

        FoodCategory savedFoodCategory = foodCategoryRepository.save(foodCategory);

        FoodCategoryResponseDto response = modelMapper.map(savedFoodCategory, FoodCategoryResponseDto.class);

        return response;
    }

    @Override
    public List<FoodCategoryResponseDto> getAllFoodCategory(AuthenticationTicketDto authTicket) {
        String userRole = authTicket.getRole();

        List<FoodCategoryResponseDto> response = null;

        List<FoodCategory> foodCategories = null;

        if (userRole == Role.ADMIN.name()) {
            foodCategories = foodCategoryRepository.findAllByStatusIn(List.of(
                    WellKnownStatus.ACTIVE.getValue(),
                    WellKnownStatus.INACTIVE.getValue()
            ));
        } else if (userRole == Role.USER.name()) {
            foodCategories = foodCategoryRepository.findAllByStatusIn(List.of(
                    WellKnownStatus.ACTIVE.getValue()
            ));
        }
        response = modelMapper.map(foodCategories, new TypeToken<List<FoodCategoryResponseDto>>(){}.getType());

        return response;
    }

    @Override
    public FoodCategoryResponseDto getFoodCategoryById(AuthenticationTicketDto authTicket, Long id) {
        String userRole = authTicket.getRole();

        FoodCategoryResponseDto response = null;

        FoodCategory foodCategory = null;

        if (userRole == Role.ADMIN.name()) {
            foodCategory = foodCategoryRepository.findByFoodCategoryIdAndStatusIn(id, List.of(
                    WellKnownStatus.ACTIVE.getValue(),
                    WellKnownStatus.INACTIVE.getValue()
            ));
        } else if (userRole == Role.USER.name()) {
            foodCategory = foodCategoryRepository.findByFoodCategoryIdAndStatusIn(id, List.of(
                    WellKnownStatus.ACTIVE.getValue()
            ));
        }

        if(foodCategory == null)
            throw new NotFoundException("Food Category Not Found!");

        response = modelMapper.map(foodCategory, FoodCategoryResponseDto.class);

        return response;
    }

    @Override
    public FoodCategoryResponseDto updateFoodCategory(AuthenticationTicketDto authTicket, Long id, FoodCategoryRequestDto request) {
        FoodCategory foodCategory = foodCategoryRepository.findByFoodCategoryIdAndStatusIn(id, List.of(
                WellKnownStatus.ACTIVE.getValue(),
                WellKnownStatus.INACTIVE.getValue()
        ));

        if(foodCategory == null)
            throw new NotFoundException("Food Category Not Found!");

        foodCategory.setFoodCategoryName(request.getFoodCategoryName());
        foodCategory.setFoodCategoryDescription(request.getFoodCategoryDescription());
        foodCategory.setUpdatedBy(authTicket.getUserId());

        FoodCategory updatedFoodCategory = foodCategoryRepository.save(foodCategory);

        FoodCategoryResponseDto response = modelMapper.map(updatedFoodCategory, FoodCategoryResponseDto.class);

        return response;
    }

    @Override
    public FoodCategoryResponseDto activeInactiveFoodCategory(AuthenticationTicketDto authTicket, Long id, String status) {
        FoodCategory foodCategory = foodCategoryRepository.findByFoodCategoryIdAndStatusIn(id, List.of(
                WellKnownStatus.ACTIVE.getValue(),
                WellKnownStatus.INACTIVE.getValue()
        ));

        if(foodCategory == null)
            throw new NotFoundException("Food Category Not Found!");

        if(status.toLowerCase().equals("active")) {
            foodCategory.setStatus(WellKnownStatus.ACTIVE.getValue());
        } else if(status.toLowerCase().equals("inactive")) {
            foodCategory.setStatus(WellKnownStatus.INACTIVE.getValue());
        }else {
            throw new BadRequestException("Invalid Status!");
        }

        foodCategory.setUpdatedBy(authTicket.getUserId());

        FoodCategory updatedFoodCategory = foodCategoryRepository.save(foodCategory);

        FoodCategoryResponseDto response = modelMapper.map(updatedFoodCategory, FoodCategoryResponseDto.class);

        return response;
    }
}
