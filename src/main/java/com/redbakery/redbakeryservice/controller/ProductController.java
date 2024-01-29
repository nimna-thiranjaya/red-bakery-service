package com.redbakery.redbakeryservice.controller;

import com.redbakery.redbakeryservice.common.ApplicationRoute;
import com.redbakery.redbakeryservice.common.CommonPaginatedResponse;
import com.redbakery.redbakeryservice.common.CommonResponse;
import com.redbakery.redbakeryservice.dto.AuthenticationTicketDto;
import com.redbakery.redbakeryservice.dto.request.ProductRequestDto;
import com.redbakery.redbakeryservice.dto.response.FoodTypeResponseDto;
import com.redbakery.redbakeryservice.dto.response.ProductResponseDto;
import com.redbakery.redbakeryservice.service.AuthenticationService;
import com.redbakery.redbakeryservice.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(ApplicationRoute.Product.Root)
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    private final AuthenticationService authenticationService;

    @PostMapping(ApplicationRoute.Product.Save)
    @PreAuthorize("hasAuthority('ADMIN')")
    ResponseEntity<CommonResponse> SaveProduct(@RequestBody @Valid ProductRequestDto request) {
        AuthenticationTicketDto authTicket = authenticationService.AuthenticationTicket();

        ProductResponseDto productResponseDto = productService.saveProduct(authTicket, request);

        ResponseEntity<CommonResponse> response = new ResponseEntity<CommonResponse>(
                new CommonResponse(true, "Product Saved!", productResponseDto),
                HttpStatus.CREATED
        );

        return response;
    }

    @GetMapping(ApplicationRoute.Product.GetAll)
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    ResponseEntity<CommonResponse> GetAllProducts() {
        AuthenticationTicketDto authTicket = authenticationService.AuthenticationTicket();

        List<ProductResponseDto> productResponseDto = productService.getAllProducts(authTicket);

        ResponseEntity<CommonResponse> response = new ResponseEntity<CommonResponse>(
                new CommonResponse(true, "", productResponseDto),
                HttpStatus.OK
        );

        return response;
    }

    @GetMapping(ApplicationRoute.Product.GetById)
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    ResponseEntity<CommonResponse> GetProductById(@PathVariable("id") Long id) {
        AuthenticationTicketDto authTicket = authenticationService.AuthenticationTicket();

        ProductResponseDto productResponseDto = productService.getProductById(authTicket, id);

        ResponseEntity<CommonResponse> response = new ResponseEntity<CommonResponse>(
                new CommonResponse(true, "", productResponseDto),
                HttpStatus.OK
        );

        return response;
    }

    @DeleteMapping(ApplicationRoute.Product.Delete)
    @PreAuthorize("hasAuthority('ADMIN')")
    ResponseEntity<CommonResponse> DeleteProductById(@PathVariable("id") Long id) {
        AuthenticationTicketDto authTicket = authenticationService.AuthenticationTicket();

        productService.deleteProductById(authTicket, id);

        ResponseEntity<CommonResponse> response = new ResponseEntity<CommonResponse>(
                new CommonResponse(true,"Product Deleted!", null),
                HttpStatus.OK
        );

        return response;
    }

    @PutMapping(ApplicationRoute.Product.ActiveInactive)
    @PreAuthorize("hasAuthority('ADMIN')")
    ResponseEntity<CommonResponse> ActiveInactiveProduct(@PathVariable("id") Long id, @RequestParam(name = "status") String status) {
        AuthenticationTicketDto authTicket = authenticationService.AuthenticationTicket();

        ProductResponseDto productResponseDto = productService.activeInactiveProduct(authTicket, id, status);

        String message = status.toLowerCase().equals("active") ? "Product Activated!" : "Product Inactivated!";

        return new ResponseEntity<CommonResponse>(
                new CommonResponse(true, message, productResponseDto),
                HttpStatus.OK
        );
    }

    @GetMapping(ApplicationRoute.Product.GetByFoodType)
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    ResponseEntity<CommonResponse> GetProductByFoodType(@PathVariable("id") Long id) {
        AuthenticationTicketDto authTicket = authenticationService.AuthenticationTicket();

        List<ProductResponseDto> productResponseDto = productService.getProductByFoodType(authTicket, id);

        ResponseEntity<CommonResponse> response = new ResponseEntity<CommonResponse>(
                new CommonResponse(true, "", productResponseDto),
                HttpStatus.OK
        );

        return response;
    }

    @GetMapping(ApplicationRoute.Product.SearchProduct)
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    ResponseEntity<CommonResponse> SearchProduct(@RequestParam(name = "name") String name) {
        AuthenticationTicketDto authTicket = authenticationService.AuthenticationTicket();

        List<ProductResponseDto> productResponseDto = productService.searchProduct(authTicket, name);

        ResponseEntity<CommonResponse> response = new ResponseEntity<CommonResponse>(
                new CommonResponse(true, "", productResponseDto),
                HttpStatus.OK
        );

        return response;
    }

    @PutMapping(ApplicationRoute.Product.Update)
    @PreAuthorize("hasAuthority('ADMIN')")
    ResponseEntity<CommonResponse> UpdateProduct(@PathVariable("id") Long id, @RequestBody @Valid ProductRequestDto request) {
        AuthenticationTicketDto authTicket = authenticationService.AuthenticationTicket();

        ProductResponseDto productResponseDto = productService.updateProduct(authTicket, id, request);

        return new ResponseEntity<CommonResponse>(
                new CommonResponse(true, "Product Updated!", productResponseDto),
                HttpStatus.OK
        );
    }

}
