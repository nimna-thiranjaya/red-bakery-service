package com.redbakery.redbakeryservice.controller;

import com.redbakery.redbakeryservice.common.ApplicationRoute;
import com.redbakery.redbakeryservice.common.CommonResponse;
import com.redbakery.redbakeryservice.dto.AuthenticationTicketDto;
import com.redbakery.redbakeryservice.dto.request.ProductRequestDto;
import com.redbakery.redbakeryservice.dto.response.ProductResponseDto;
import com.redbakery.redbakeryservice.service.AuthenticationService;
import com.redbakery.redbakeryservice.service.ProductService;
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
@RequestMapping(ApplicationRoute.Product.Root)
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    private final AuthenticationService authenticationService;

    @PostMapping(ApplicationRoute.Product.Save)
    @PreAuthorize("hasAuthority('ADMIN')")
    ResponseEntity<CommonResponse> SaveProduct(@RequestBody @Valid ProductRequestDto request){
        AuthenticationTicketDto authTicket = authenticationService.AuthenticationTicket();

        ProductResponseDto productResponseDto = productService.saveProduct(authTicket, request);

        ResponseEntity<CommonResponse> response = new ResponseEntity<CommonResponse>(
                new CommonResponse(true, "Product Saved!", productResponseDto),
                HttpStatus.CREATED
        );

        return response;
    }
}
