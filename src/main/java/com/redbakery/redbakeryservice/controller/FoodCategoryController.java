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
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(ApplicationRoute.FoodCategory.Root)
public class FoodCategoryController {
    private final FoodCategoryService foodCategoryService;

    private final AuthenticationService authenticationService;

    @PostMapping(ApplicationRoute.FoodCategory.Save)
    @PreAuthorize("hasAuthority('ADMIN')")
    ResponseEntity<CommonResponse> SaveFoodCategory(@RequestBody @Valid FoodCategoryRequestDto request) {

        AuthenticationTicketDto authTicket = authenticationService.AuthenticationTicket();

        FoodCategoryResponseDto foodCategoryResponseDto = foodCategoryService.saveFoodCategory(authTicket, request);

        ResponseEntity<CommonResponse> response = new ResponseEntity<CommonResponse>(
                new CommonResponse(true, "Food Category Saved!", foodCategoryResponseDto),
                HttpStatus.CREATED
        );

        return response;
    }

    @GetMapping(ApplicationRoute.FoodCategory.GetAll)
    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    ResponseEntity<CommonResponse> GetAllFoodTypes() {

        AuthenticationTicketDto authTicket = authenticationService.AuthenticationTicket();

        List<FoodCategoryResponseDto> foodCategories = foodCategoryService.getAllFoodCategory(authTicket);

        ResponseEntity<CommonResponse> response = new ResponseEntity<CommonResponse>(
                new CommonResponse(true, "", foodCategories),
                HttpStatus.OK
        );

        return response;
    }

    @GetMapping(ApplicationRoute.FoodCategory.GetById)
    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    ResponseEntity<CommonResponse> GetFoodCategoryById(@PathVariable Long id) {

        AuthenticationTicketDto authTicket = authenticationService.AuthenticationTicket();

        FoodCategoryResponseDto foodCategory = foodCategoryService.getFoodCategoryById(authTicket, id);

        ResponseEntity<CommonResponse> response = new ResponseEntity<CommonResponse>(
                new CommonResponse(true, "", foodCategory),
                HttpStatus.OK
        );

        return response;
    }

    @PutMapping(ApplicationRoute.FoodCategory.Update)
    @PreAuthorize("hasAuthority('ADMIN')")
    ResponseEntity<CommonResponse> UpdateFoodCategory(@PathVariable Long id, @RequestBody @Valid FoodCategoryRequestDto request) {

        AuthenticationTicketDto authTicket = authenticationService.AuthenticationTicket();

        FoodCategoryResponseDto foodCategoryResponseDto = foodCategoryService.updateFoodCategory(authTicket, id, request);

        ResponseEntity<CommonResponse> response = new ResponseEntity<CommonResponse>(
                new CommonResponse(true, "Food Category Updated!", foodCategoryResponseDto),
                HttpStatus.OK
        );

        return response;
    }

    @PutMapping(ApplicationRoute.FoodCategory.ActiveInactive)
    @PreAuthorize("hasAuthority('ADMIN')")
    ResponseEntity<CommonResponse> ActiveInactiveFoodCategory(@PathVariable Long id, @RequestParam(name = "status") String status) {

            AuthenticationTicketDto authTicket = authenticationService.AuthenticationTicket();

            FoodCategoryResponseDto foodCategoryResponseDto = foodCategoryService.activeInactiveFoodCategory(authTicket, id, status);

            String message = status.toLowerCase().equals("active") ? "Food Category Activated!" : "Food Category Inactivated!";

            ResponseEntity<CommonResponse> response = new ResponseEntity<CommonResponse>(
                    new CommonResponse(true, message, foodCategoryResponseDto),
                    HttpStatus.OK
            );

            return response;

    }
}
