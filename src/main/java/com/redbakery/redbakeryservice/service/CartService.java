package com.redbakery.redbakeryservice.service;

import com.redbakery.redbakeryservice.dto.AuthenticationTicketDto;
import com.redbakery.redbakeryservice.dto.request.CartRequestDto;

public interface CartService {
    void addToCart(AuthenticationTicketDto authTicket, CartRequestDto cartRequestDto);
}
