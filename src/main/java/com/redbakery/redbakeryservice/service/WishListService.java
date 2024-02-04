package com.redbakery.redbakeryservice.service;

import com.redbakery.redbakeryservice.dto.AuthenticationTicketDto;
import com.redbakery.redbakeryservice.dto.response.WishListResponseDto;

import java.util.List;

public interface WishListService {
    void addProductToWishList(AuthenticationTicketDto authTicket, Long id);

    List<WishListResponseDto> getWishList(AuthenticationTicketDto authTicket);

    void removeFromWishList(AuthenticationTicketDto authTicket, Long id);
}
