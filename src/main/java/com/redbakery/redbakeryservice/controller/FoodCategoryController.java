package com.redbakery.redbakeryservice.controller;

import com.redbakery.redbakeryservice.common.ApplicationRoute;
import com.redbakery.redbakeryservice.common.CommonResponse;
import com.redbakery.redbakeryservice.dto.AuthenticationTicketDto;
import com.redbakery.redbakeryservice.dto.request.FoodCategoryRequestDto;
import com.redbakery.redbakeryservice.dto.response.FoodCategoryResponseDto;
import com.redbakery.redbakeryservice.service.AuthenticationService;
import com.redbakery.redbakeryservice.service.FoodCategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(ApplicationRoute.FoodCategory.Root)
public class FoodCategoryController {
    private final FoodCategoryService foodCategoryService;

    private final AuthenticationService authenticationService;

    @PostMapping(ApplicationRoute.FoodCategory.Save)
    ResponseEntity<CommonResponse> SaveFoodCategory(@RequestBody @Valid FoodCategoryRequestDto request) {

        AuthenticationTicketDto authTicket = authenticationService.AuthenticationTicket();

        FoodCategoryResponseDto foodCategoryResponseDto = foodCategoryService.saveFoodCategory(authTicket, request);

        ResponseEntity<CommonResponse> response = new ResponseEntity<CommonResponse>(
                new CommonResponse(true, "Food Category Saved!", foodCategoryResponseDto),
                HttpStatus.CREATED
        );

        return response;
    }
}
