package com.redbakery.redbakeryservice.service;

import com.redbakery.redbakeryservice.dto.AuthenticationTicketDto;

public interface WishListService {
    void addProductToWishList(AuthenticationTicketDto authTicket, Long id);
}
