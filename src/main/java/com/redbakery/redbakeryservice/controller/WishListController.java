package com.redbakery.redbakeryservice.controller;

import com.redbakery.redbakeryservice.common.ApplicationRoute;
import com.redbakery.redbakeryservice.common.CommonResponse;
import com.redbakery.redbakeryservice.dto.AuthenticationTicketDto;
import com.redbakery.redbakeryservice.service.AuthenticationService;
import com.redbakery.redbakeryservice.service.WishListService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(ApplicationRoute.WishList.Root)
@RequiredArgsConstructor
public class WishListController {

    private final AuthenticationService authenticationService;

    private final WishListService wishListService;

    @PostMapping(ApplicationRoute.WishList.AddToWishList)
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<CommonResponse> AddProductToWishList(@PathVariable Long id){
        AuthenticationTicketDto authTicket = authenticationService.AuthenticationTicket();

        wishListService.addProductToWishList(authTicket, id);

        return new ResponseEntity<CommonResponse>(
                new CommonResponse(true, "Product Added To Wishlist!", null),
                HttpStatus.CREATED
        );
    }
}
