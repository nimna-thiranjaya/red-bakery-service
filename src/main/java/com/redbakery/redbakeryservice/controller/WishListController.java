package com.redbakery.redbakeryservice.controller;

import com.redbakery.redbakeryservice.common.ApplicationRoute;
import com.redbakery.redbakeryservice.common.CommonResponse;
import com.redbakery.redbakeryservice.dto.AuthenticationTicketDto;
import com.redbakery.redbakeryservice.dto.response.WishListResponseDto;
import com.redbakery.redbakeryservice.service.AuthenticationService;
import com.redbakery.redbakeryservice.service.WishListService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(ApplicationRoute.WishList.Root)
@RequiredArgsConstructor
public class WishListController {

    private final AuthenticationService authenticationService;

    private final WishListService wishListService;

    @PostMapping(ApplicationRoute.WishList.AddToWishList)
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<CommonResponse> AddProductToWishList(@PathVariable Long id){
        AuthenticationTicketDto authTicket = authenticationService.AuthenticationTicket();

        wishListService.addProductToWishList(authTicket, id);

        return new ResponseEntity<CommonResponse>(
                new CommonResponse(true, "Product Added To Wishlist!", null),
                HttpStatus.CREATED
        );
    }

    @GetMapping(ApplicationRoute.WishList.GetWishList)
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<CommonResponse> GetWishList(){
        AuthenticationTicketDto authTicket = authenticationService.AuthenticationTicket();

        List<WishListResponseDto> wishList = wishListService.getWishList(authTicket);

        return new ResponseEntity<CommonResponse>(
                new CommonResponse(true, "", wishList ),
                HttpStatus.OK
        );
    }

    @DeleteMapping(ApplicationRoute.WishList.RemoveFromWishList)
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<CommonResponse> RemoveFromWishList(@PathVariable Long id){
        AuthenticationTicketDto authTicket = authenticationService.AuthenticationTicket();

        wishListService.removeFromWishList(authTicket, id);

        return new ResponseEntity<CommonResponse>(
                new CommonResponse(true, "Product Removed From Wishlist!", null),
                HttpStatus.OK
        );
    }
}
