package com.redbakery.redbakeryservice.service;

import com.redbakery.redbakeryservice.common.CommonPaginatedResponse;
import com.redbakery.redbakeryservice.dto.AuthenticationTicketDto;
import com.redbakery.redbakeryservice.dto.request.ProductRequestDto;
import com.redbakery.redbakeryservice.dto.response.ProductResponseDto;

import java.util.List;

public interface ProductService {
    ProductResponseDto saveProduct(AuthenticationTicketDto authTicket, ProductRequestDto request);

    List<ProductResponseDto> getAllProducts(AuthenticationTicketDto authTicket);

    ProductResponseDto getProductById(AuthenticationTicketDto authTicket, Long id);

    void deleteProductById(AuthenticationTicketDto authTicket, Long id);

    ProductResponseDto activeInactiveProduct(AuthenticationTicketDto authTicket, Long id, String status);

    List<ProductResponseDto> getProductByFoodType(AuthenticationTicketDto authTicket, Long id);

    List<ProductResponseDto> searchProduct(AuthenticationTicketDto authTicket, String name);
}
