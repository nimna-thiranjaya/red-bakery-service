package com.redbakery.redbakeryservice.controller;

import com.redbakery.redbakeryservice.common.ApplicationRoute;
import com.redbakery.redbakeryservice.common.CommonResponse;
import com.redbakery.redbakeryservice.dto.AuthenticationTicketDto;
import com.redbakery.redbakeryservice.dto.request.CartRequestDto;
import com.redbakery.redbakeryservice.service.AuthenticationService;
import com.redbakery.redbakeryservice.service.CartService;
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
@RequestMapping(ApplicationRoute.Cart.Root)
public class CartController {
    private final CartService cartService;

    private final AuthenticationService authenticationService;

    @PostMapping(ApplicationRoute.Cart.AddToCart)
    @PreAuthorize("hasAuthority('USER')")
    ResponseEntity<CommonResponse> AddToCart(@RequestBody @Valid CartRequestDto cartRequestDto) {
        AuthenticationTicketDto authTicket = authenticationService.AuthenticationTicket();

        cartService.addToCart(authTicket, cartRequestDto);

        return new ResponseEntity<CommonResponse>(
                new CommonResponse(true, "Product Added To Cart!", null),
                HttpStatus.CREATED
        );

    }


}
