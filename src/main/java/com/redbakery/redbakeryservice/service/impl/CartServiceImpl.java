package com.redbakery.redbakeryservice.service.impl;

import com.redbakery.redbakeryservice.common.WellKnownCartStatus;
import com.redbakery.redbakeryservice.common.WellKnownStatus;
import com.redbakery.redbakeryservice.dto.AuthenticationTicketDto;
import com.redbakery.redbakeryservice.dto.request.CartRequestDto;
import com.redbakery.redbakeryservice.dto.response.CartResponseDto;
import com.redbakery.redbakeryservice.dto.response.GetCartResponseDto;
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

import java.util.ArrayList;
import java.util.Date;
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

        if (product == null) throw new RuntimeException("Product not found!");

        List<CartDetail> cartDetails = cartDetailRepository.findAllByCartAndProductAndStatusIn(cart, product, List.of(WellKnownStatus.ACTIVE.getValue()));

        if (!cartDetails.isEmpty()) throw new BadRequestException("Product already in cart!");

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

        if (cart == null) throw new BadRequestException("Cart not found!");

        for (CartRequestDto cartRequestDto : request) {
            Product product = productRepository.findByProductIdAndStatusIn(cartRequestDto.getProductId(), List.of(WellKnownStatus.ACTIVE.getValue()));

            if (product == null) throw new BadRequestException("Product not found!");

            List<CartDetail> cartDetails = cartDetailRepository.findAllByCartAndProductAndStatusIn(cart, product, List.of(WellKnownStatus.ACTIVE.getValue()));

            if (!cartDetails.isEmpty()) {
                cartDetails.forEach(cartDetail -> {
                    if (Objects.equals(cartDetail.getProduct().getProductId(), cartRequestDto.getProductId()))
                        cartDetail.setQuantity(cartRequestDto.getQuantity());

                    cartDetailRepository.save(cartDetail);
                });
            }
        }

        List<CartDetail> cartDetails = cartDetailRepository.findAllByCartAndStatusIn(cart, List.of(WellKnownStatus.ACTIVE.getValue()));

        if (!cartDetails.isEmpty()) {
            cart.setTotalQuantity(cartDetails.stream().mapToInt(CartDetail::getQuantity).sum());
            cartRepository.save(cart);
        }
    }

    @Override
    @Transactional
    public void removeFromCart(AuthenticationTicketDto authTicket, Long cartDetailId) {
        Cart cart = cartRepository.findByUserAndStatusIn(authTicket.getUser(), List.of(WellKnownCartStatus.PENDING.getValue()));

        if (cart == null) throw new BadRequestException("Cart not found!");

        CartDetail cartDetail = cartDetailRepository.findByCartDetailsIdAndStatusIn(cartDetailId, List.of(WellKnownStatus.ACTIVE.getValue()));

        if (cartDetail == null) throw new BadRequestException("Cart detail not found!");

        cartDetail.setStatus(WellKnownStatus.DELETED.getValue());
        cartDetailRepository.save(cartDetail);

        cart.setTotalQuantity(cart.getTotalQuantity() - cartDetail.getQuantity());
        cartRepository.save(cart);
    }

    @Override
    public GetCartResponseDto getCart(AuthenticationTicketDto authTicket) {
        Cart cart = cartRepository.findByUserAndStatusIn(authTicket.getUser(), List.of(WellKnownCartStatus.PENDING.getValue()));
        GetCartResponseDto response = new GetCartResponseDto();

        if (cart == null){
            cart = new Cart();
            cart.setUser(authTicket.getUser());
            cart.setStatus(WellKnownCartStatus.PENDING.getValue());
            cart.setTotalQuantity(0);
            cart = cartRepository.save(cart);
        }

        List<CartDetail> cartDetails = cartDetailRepository.findAllByCartAndStatusIn(cart, List.of(WellKnownStatus.ACTIVE.getValue()));

        List<CartResponseDto> cartResponses = new ArrayList<>();

        double totalAmount = 0.0;
        double discount = 0.0;
        double payableAmount = 0.0;
        int totalItems = 0;

        for (CartDetail cartDetail : cartDetails) {
            if (cartDetail.getProduct().getStatus() == WellKnownStatus.ACTIVE.getValue()) {
                CartResponseDto cartResponseDto = mapToCartDetailsToCartResponseDto(cartDetail);
                totalAmount += cartResponseDto.getUnitPrice() * cartResponseDto.getQuantity();
                discount += cartResponseDto.getDiscountPrice();
                payableAmount += cartResponseDto.getTotalPrice();
                totalItems += cartResponseDto.getQuantity();
                cartResponses.add(cartResponseDto);
            }
        }

        response.setCartId(cart.getCartId());
        response.setCartItems(cartResponses);
        response.setTotalAmount(totalAmount);
        response.setDiscount(discount);
        response.setPayableAmount(payableAmount);
        response.setTotalItems(totalItems);

        return response;
    }

    private CartResponseDto mapToCartDetailsToCartResponseDto(CartDetail cartDetail) {
        Date currentDate = new Date();
        CartResponseDto cartResponseDto = new CartResponseDto();
        Cart cart = cartDetail.getCart();
        Product product = cartDetail.getProduct();

        cartResponseDto.setCartDetailId(cartDetail.getCartDetailsId());
        cartResponseDto.setCartId(cart.getCartId());
        cartResponseDto.setProductId(product.getProductId());
        cartResponseDto.setProductName(product.getProductName());
        cartResponseDto.setQuantity(cartDetail.getQuantity());
        cartResponseDto.setUnitPrice(product.getProductPrice());

        if (cartDetail.getProduct().getIsDiscounted()) {
            double discountedPrice = 0.0;
            if (currentDate.after(product.getDiscount().getStartDate()) && currentDate.before(product.getDiscount().getEndDate())) {
                discountedPrice = (product.getProductPrice() * product.getDiscount().getDiscountPercentage() / 100);
                cartResponseDto.setDiscountPercentage(product.getDiscount().getDiscountPercentage());
            }else {
                cartResponseDto.setDiscountPercentage(0.0);
            }
            cartResponseDto.setDiscountPrice(discountedPrice * cartDetail.getQuantity());
            cartResponseDto.setTotalPrice((product.getProductPrice() - discountedPrice) * cartDetail.getQuantity());
        }
        return cartResponseDto;
    }
}