package com.redbakery.redbakeryservice.service.impl;

import com.redbakery.redbakeryservice.common.WellKnownStatus;
import com.redbakery.redbakeryservice.dto.AuthenticationTicketDto;
import com.redbakery.redbakeryservice.dto.request.FoodTypeRequestDto;
import com.redbakery.redbakeryservice.dto.response.FoodCategoryResponseDto;
import com.redbakery.redbakeryservice.dto.response.FoodTypeResponseDto;
import com.redbakery.redbakeryservice.exception.BadRequestException;
import com.redbakery.redbakeryservice.exception.NotFoundException;
import com.redbakery.redbakeryservice.model.FoodCategory;
import com.redbakery.redbakeryservice.model.FoodType;
import com.redbakery.redbakeryservice.model.Role;
import com.redbakery.redbakeryservice.repository.FoodCategoryRepository;
import com.redbakery.redbakeryservice.repository.FoodTypeRepository;
import com.redbakery.redbakeryservice.service.FoodTypeService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FoodTypeServiceImpl implements FoodTypeService {
    private final FoodTypeRepository foodTypeRepository;

    private final FoodCategoryRepository foodCategoryRepository;

    private final ModelMapper modelMapper;

    @Override
    public FoodTypeResponseDto saveFoodType(AuthenticationTicketDto authTicket, FoodTypeRequestDto request) {
        FoodCategory foodCategory = foodCategoryRepository.findByFoodCategoryIdAndStatusIn(request.getFoodCategoryId(), List.of(WellKnownStatus.ACTIVE.getValue()));

        if(foodCategory == null)
            throw new BadRequestException("Invalid Food Category!");

        Boolean isExist = foodTypeRepository.existsByFoodTypeNameAndFoodCategoryAndStatusNot(request.getFoodTypeName(), foodCategory, WellKnownStatus.DELETED.getValue());

        if (isExist)
            throw new BadRequestException("Food Type Already Exist!");

        FoodType foodType = modelMapper.map(request, FoodType.class);

        foodType.setAddedBy(authTicket.getUserId());
        foodType.setUpdatedBy(authTicket.getUserId());
        foodType.setStatus(WellKnownStatus.ACTIVE.getValue());

        FoodType savedFoodType = foodTypeRepository.save(foodType);

        FoodTypeResponseDto response = modelMapper.map(savedFoodType, FoodTypeResponseDto.class);

        return response;
    }

    @Override
    public List<FoodTypeResponseDto> getAllFoodType(AuthenticationTicketDto authTicket) {
        String userRole = authTicket.getRole();

        List<FoodType> foodTypeList = null;

        if(userRole.equals(Role.ADMIN.name())){
            foodTypeList = foodTypeRepository.findAllByStatusIn(List.of(WellKnownStatus.ACTIVE.getValue(), WellKnownStatus.INACTIVE.getValue()));
        }else if(userRole.equals(Role.USER.name())){
            foodTypeList = foodTypeRepository.findAllByStatusIn(List.of(WellKnownStatus.ACTIVE.getValue()));
        }
        List<FoodTypeResponseDto> response = modelMapper.map(foodTypeList, new TypeToken<List<FoodTypeResponseDto>>(){}.getType());

        return response;
    }

    @Override
    public FoodTypeResponseDto getFoodTypeById(AuthenticationTicketDto authTicket, Long id) {
        String userRole = authTicket.getRole();

        FoodType foodType = null;

        if(userRole.equals(Role.ADMIN.name())){
            foodType = foodTypeRepository.findByFoodTypeIdAndStatusIn(id, List.of(WellKnownStatus.ACTIVE.getValue(), WellKnownStatus.INACTIVE.getValue()));
        }else if(userRole.equals(Role.USER.name())){
            foodType = foodTypeRepository.findByFoodTypeIdAndStatusIn(id, List.of(WellKnownStatus.ACTIVE.getValue()));
        }

        if(foodType == null)
            throw new BadRequestException("Invalid Food Type!");

        FoodTypeResponseDto response = modelMapper.map(foodType, FoodTypeResponseDto.class);

        return response;
    }

    @Override
    public FoodTypeResponseDto updateFoodType(AuthenticationTicketDto authTicket, Long id, FoodTypeRequestDto request) {
        FoodCategory foodCategory = foodCategoryRepository.findByFoodCategoryIdAndStatusIn(request.getFoodCategoryId(), List.of(WellKnownStatus.ACTIVE.getValue()));

        if(foodCategory == null)
            throw new BadRequestException("Invalid Food Category!");

        FoodType foodType = foodTypeRepository.findByFoodTypeIdAndStatusIn(id, List.of(WellKnownStatus.ACTIVE.getValue(), WellKnownStatus.INACTIVE.getValue()));

        if(foodType == null)
            throw new BadRequestException("Invalid Food Type!");

        if(!foodType.getFoodTypeName().equals(request.getFoodTypeName())){
            Boolean isExist = foodTypeRepository.existsByFoodTypeNameAndFoodCategoryAndStatusNot(request.getFoodTypeName(), foodCategory, WellKnownStatus.DELETED.getValue());

            if (isExist)
                throw new BadRequestException("Food Type Already Exist!");
        }

        foodType.setFoodTypeName(request.getFoodTypeName());
        foodType.setFoodCategory(foodCategory);
        foodType.setUpdatedBy(authTicket.getUserId());

        FoodType updatedFoodType = foodTypeRepository.save(foodType);

        FoodTypeResponseDto response = modelMapper.map(updatedFoodType, FoodTypeResponseDto.class);

        return response;
    }

    @Override
    public FoodTypeResponseDto activeInactiveFoodType(AuthenticationTicketDto authTicket, Long id, String status) {
        FoodType foodType = foodTypeRepository.findByFoodTypeIdAndStatusIn(id, List.of(
                WellKnownStatus.ACTIVE.getValue(),
                WellKnownStatus.INACTIVE.getValue()
        ));

        if(foodType == null)
            throw new NotFoundException("Food Type Not Found!");

        if(status.equalsIgnoreCase("active")) {
            foodType.setStatus(WellKnownStatus.ACTIVE.getValue());
        } else if(status.equalsIgnoreCase("inactive")) {
            foodType.setStatus(WellKnownStatus.INACTIVE.getValue());
        }else {
            throw new BadRequestException("Invalid Status!");
        }

        foodType.setUpdatedBy(authTicket.getUserId());

        FoodType updatedFoodType = foodTypeRepository.save(foodType);

        FoodTypeResponseDto response = modelMapper.map(updatedFoodType, FoodTypeResponseDto.class);

        return response;
    }

    @Override
    public FoodTypeResponseDto deleteFoodType(AuthenticationTicketDto authTicket, Long id) {
        FoodType foodType = foodTypeRepository.findByFoodTypeIdAndStatusIn(id, List.of(
                WellKnownStatus.ACTIVE.getValue(),
                WellKnownStatus.INACTIVE.getValue()
        ));

        if(foodType == null)
            throw new NotFoundException("Food Type Not Found!");

        foodType.setStatus(WellKnownStatus.DELETED.getValue());
        foodType.setUpdatedBy(authTicket.getUserId());

        FoodType updatedFoodType = foodTypeRepository.save(foodType);

        FoodTypeResponseDto response = modelMapper.map(updatedFoodType, FoodTypeResponseDto.class);

        return response;
    }

    @Override
    public List<FoodTypeResponseDto> getFoodTypeByCategory(AuthenticationTicketDto authTicket, Long id) {
        FoodCategory foodCategory = foodCategoryRepository.findByFoodCategoryIdAndStatusIn(id, List.of(WellKnownStatus.ACTIVE.getValue()));

        if(foodCategory == null)
            throw new BadRequestException("Invalid Food Category!");

        List<FoodType> foodTypeList = foodTypeRepository.findAllByFoodCategoryAndStatusIn(foodCategory, List.of(WellKnownStatus.ACTIVE.getValue()));

        List<FoodTypeResponseDto> response = modelMapper.map(foodTypeList, new TypeToken<List<FoodTypeResponseDto>>(){}.getType());

        return response;
    }

}
