package com.redbakery.redbakeryservice.controller;

import com.redbakery.redbakeryservice.common.ApplicationRoute;
import com.redbakery.redbakeryservice.common.CommonResponse;
import com.redbakery.redbakeryservice.dto.AuthenticationTicketDto;
import com.redbakery.redbakeryservice.dto.request.FoodTypeRequestDto;
import com.redbakery.redbakeryservice.dto.response.FoodTypeResponseDto;
import com.redbakery.redbakeryservice.service.AuthenticationService;
import com.redbakery.redbakeryservice.service.FoodTypeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(ApplicationRoute.FoodType.Root)
public class FoodTypeController {
    private final FoodTypeService foodTypeService;

    private final AuthenticationService authenticationService;

    @PostMapping(ApplicationRoute.FoodType.Save)
    @PreAuthorize("hasAuthority('ADMIN')")
    ResponseEntity<CommonResponse> SaveFoodType(@RequestBody @Valid FoodTypeRequestDto request) {

        AuthenticationTicketDto authTicket = authenticationService.AuthenticationTicket();

        FoodTypeResponseDto foodTypeResponseDto = foodTypeService.saveFoodType(authTicket, request);

        ResponseEntity<CommonResponse> response = new ResponseEntity<CommonResponse>(
                new CommonResponse(true, "Food Type Saved!", foodTypeResponseDto),
                HttpStatus.CREATED
        );

        return response;
    }

    @GetMapping(ApplicationRoute.FoodType.GetAll)
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    ResponseEntity<CommonResponse> GetAllFoodType() {
        AuthenticationTicketDto authTicket = authenticationService.AuthenticationTicket();

        List<FoodTypeResponseDto> foodTypes = foodTypeService.getAllFoodType(authTicket);

        return new ResponseEntity<CommonResponse>(
                new CommonResponse(true, "", foodTypes),
                HttpStatus.OK
        );
    }

    @GetMapping(ApplicationRoute.FoodType.GetById)
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    ResponseEntity<CommonResponse> GetFoodTypeById(@PathVariable("id") Long id) {
        AuthenticationTicketDto authTicket = authenticationService.AuthenticationTicket();

        FoodTypeResponseDto foodType = foodTypeService.getFoodTypeById(authTicket, id);

        return new ResponseEntity<CommonResponse>(
                new CommonResponse(true, "", foodType),
                HttpStatus.OK
        );
    }

    @PutMapping(ApplicationRoute.FoodType.Update)
    @PreAuthorize("hasAuthority('ADMIN')")
    ResponseEntity<CommonResponse> UpdateFoodType(@PathVariable("id") Long id, @RequestBody @Valid FoodTypeRequestDto request) {
        AuthenticationTicketDto authTicket = authenticationService.AuthenticationTicket();

        FoodTypeResponseDto foodType = foodTypeService.updateFoodType(authTicket, id, request);

        return new ResponseEntity<CommonResponse>(
                new CommonResponse(true, "Food Type Updated!", foodType),
                HttpStatus.OK
        );
    }

    @PutMapping(ApplicationRoute.FoodType.ActiveInactive)
    @PreAuthorize("hasAuthority('ADMIN')")
    ResponseEntity<CommonResponse> ActiveInactiveFoodType(@PathVariable("id") Long id, @RequestParam(name = "status") String status) {
        AuthenticationTicketDto authTicket = authenticationService.AuthenticationTicket();

        FoodTypeResponseDto foodType = foodTypeService.activeInactiveFoodType(authTicket, id, status);

        String message = status.toLowerCase().equals("active") ? "Food Type Activated!" : "Food Type Inactivated!";

        return new ResponseEntity<CommonResponse>(
                new CommonResponse(true, message, null),
                HttpStatus.OK
        );
    }

    @DeleteMapping(ApplicationRoute.FoodType.Delete)
    @PreAuthorize("hasAuthority('ADMIN')")
    ResponseEntity<CommonResponse> DeleteFoodType(@PathVariable("id") Long id) {
        AuthenticationTicketDto authTicket = authenticationService.AuthenticationTicket();

        FoodTypeResponseDto foodType = foodTypeService.deleteFoodType(authTicket, id);

        return new ResponseEntity<CommonResponse>(
                new CommonResponse(true, "Food Type Deleted!", null),
                HttpStatus.OK
        );
    }

    @GetMapping(ApplicationRoute.FoodType.GetByCategory)
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    ResponseEntity<CommonResponse> GetFoodTypeByCategory(@PathVariable("id") Long id) {
        AuthenticationTicketDto authTicket = authenticationService.AuthenticationTicket();

        List<FoodTypeResponseDto> foodTypes = foodTypeService.getFoodTypeByCategory(authTicket, id);

        return new ResponseEntity<CommonResponse>(
                new CommonResponse(true, "", foodTypes),
                HttpStatus.OK
        );
    }



}
