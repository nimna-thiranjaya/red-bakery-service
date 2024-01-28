package com.redbakery.redbakeryservice.service;

import com.redbakery.redbakeryservice.dto.AuthenticationTicketDto;
import com.redbakery.redbakeryservice.dto.request.ProductRequestDto;
import com.redbakery.redbakeryservice.dto.response.ProductResponseDto;

public interface ProductService {
    ProductResponseDto saveProduct(AuthenticationTicketDto authTicket, ProductRequestDto request);
}
