package com.redbakery.redbakeryservice.service.impl;

import com.redbakery.redbakeryservice.common.WellKnownCartStatus;
import com.redbakery.redbakeryservice.common.WellKnownStatus;
import com.redbakery.redbakeryservice.dto.AuthenticationTicketDto;
import com.redbakery.redbakeryservice.dto.request.CartRequestDto;
import com.redbakery.redbakeryservice.exception.BadRequestException;
import com.redbakery.redbakeryservice.model.Cart;
import com.redbakery.redbakeryservice.model.CartDetail;
import com.redbakery.redbakeryservice.model.Product;
import com.redbakery.redbakeryservice.repository.CartDetailRepository;
import com.redbakery.redbakeryservice.repository.CartRepository;
import com.redbakery.redbakeryservice.repository.ProductRepository;
import com.redbakery.redbakeryservice.service.CartService;
import com.redbakery.redbakeryservice.service.ProductService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final ProductRepository productRepository;

    private final CartRepository cartRepository;

    private final CartDetailRepository cartDetailRepository;

    @Override
    @Transactional
    public void addToCart(AuthenticationTicketDto authTicket, CartRequestDto cartRequestDto) {
        Cart cart = cartRepository.findByUserAndStatusIn(authTicket.getUser(), List.of(WellKnownCartStatus.PENDING.getValue()));

        // If cart is not found, create a new cart
        if (cart == null) {
            cart = new Cart();
            cart.setUser(authTicket.getUser());
            cart.setStatus(WellKnownCartStatus.PENDING.getValue());
            cart.setTotalQuantity(0);
            cart = cartRepository.save(cart);
        }

        Product product = productRepository.findByProductIdAndStatusIn(cartRequestDto.getProductId(), List.of(WellKnownStatus.ACTIVE.getValue()));

        if (product == null)
            throw new RuntimeException("Product not found!");

        List<CartDetail> cartDetails = cartDetailRepository.findAllByCartAndProductAndStatusIn(cart, product, List.of(WellKnownStatus.ACTIVE.getValue()));

        if (!cartDetails.isEmpty())
            throw new BadRequestException("Product already in cart!");

        CartDetail cartDetail = new CartDetail();
        cartDetail.setCart(cart);
        cartDetail.setProduct(product);
        cartDetail.setQuantity(cartRequestDto.getQuantity());
        cartDetail.setStatus(WellKnownStatus.ACTIVE.getValue());

        cartDetailRepository.save(cartDetail);

        cart.setTotalQuantity(cart.getTotalQuantity() + cartRequestDto.getQuantity());
        cartRepository.save(cart);
    }

    @Override
    @Transactional
    public void updateCart(AuthenticationTicketDto authTicket, List<CartRequestDto> request) {
        Cart cart = cartRepository.findByUserAndStatusIn(authTicket.getUser(), List.of(WellKnownCartStatus.PENDING.getValue()));

        if (cart == null)
            throw new BadRequestException("Cart not found!");

        for (CartRequestDto cartRequestDto : request) {
            Product product = productRepository.findByProductIdAndStatusIn(cartRequestDto.getProductId(), List.of(WellKnownStatus.ACTIVE.getValue()));

            if (product == null)
                throw new BadRequestException("Product not found!");

            List<CartDetail> cartDetails = cartDetailRepository.findAllByCartAndProductAndStatusIn(cart, product, List.of(WellKnownStatus.ACTIVE.getValue()));

            if (!cartDetails.isEmpty()){
                cartDetails.forEach(cartDetail -> {
                  if(Objects.equals(cartDetail.getProduct().getProductId(), cartRequestDto.getProductId()))
                      cartDetail.setQuantity(cartRequestDto.getQuantity());

                    cartDetailRepository.save(cartDetail);
                });
            }
        }

        List<CartDetail> cartDetails = cartDetailRepository.findAllByCartAndStatusIn(cart, List.of(WellKnownStatus.ACTIVE.getValue()));

        if(!cartDetails.isEmpty()){
            cart.setTotalQuantity(cartDetails.stream().mapToInt(CartDetail::getQuantity).sum());
            cartRepository.save(cart);
        }
    }
}
