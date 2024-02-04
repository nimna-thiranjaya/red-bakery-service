package com.redbakery.redbakeryservice.service;

import com.redbakery.redbakeryservice.dto.AuthenticationTicketDto;
import com.redbakery.redbakeryservice.dto.request.CartRequestDto;

import java.util.List;

public interface CartService {
    void addToCart(AuthenticationTicketDto authTicket, CartRequestDto cartRequestDto);

    void updateCart(AuthenticationTicketDto authTicket, List<CartRequestDto> request);

    void removeFromCart(AuthenticationTicketDto authTicket, Long cartDetailId);
}
