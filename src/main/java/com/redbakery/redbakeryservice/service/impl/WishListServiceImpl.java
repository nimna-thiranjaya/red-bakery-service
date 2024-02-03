package com.redbakery.redbakeryservice.service.impl;

import com.redbakery.redbakeryservice.common.WellKnownStatus;
import com.redbakery.redbakeryservice.dto.AuthenticationTicketDto;
import com.redbakery.redbakeryservice.exception.BadRequestException;
import com.redbakery.redbakeryservice.model.Product;
import com.redbakery.redbakeryservice.model.User;
import com.redbakery.redbakeryservice.model.WishList;
import com.redbakery.redbakeryservice.repository.ProductRepository;
import com.redbakery.redbakeryservice.repository.UserRepository;
import com.redbakery.redbakeryservice.repository.WishListRepository;
import com.redbakery.redbakeryservice.service.WishListService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WishListServiceImpl implements WishListService {

    private final ProductRepository productRepository;

    private final WishListRepository wishListRepository;

    private final UserRepository userRepository;

    @Override
    public void addProductToWishList(AuthenticationTicketDto authTicket, Long id) {
        Product product = productRepository.findByProductIdAndStatusIn(id, List.of(WellKnownStatus.ACTIVE.getValue()));

        if (product == null)
            throw new BadRequestException("Invalid Product Id!");

        List<WishList> wishListItemCheck = wishListRepository.findAllByUserAndStatusIn(authTicket.getUser(), List.of(WellKnownStatus.ACTIVE.getValue()));

        if (!wishListItemCheck.isEmpty())
            throw new BadRequestException("Product already exists in wishlist!");

        WishList wishList = new WishList();

        wishList.setProduct(product);
        wishList.setUser(authTicket.getUser());
        wishList.setStatus(WellKnownStatus.ACTIVE.getValue());

        wishListRepository.save(wishList);
    }
}
