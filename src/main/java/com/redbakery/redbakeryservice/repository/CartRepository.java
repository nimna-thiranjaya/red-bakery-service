package com.redbakery.redbakeryservice.repository;

import com.redbakery.redbakeryservice.model.Cart;
import com.redbakery.redbakeryservice.model.CartDetail;
import com.redbakery.redbakeryservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartRepository extends JpaRepository<Cart, Long> {
    Cart findByUserAndStatusIn(User user, List<Integer> status);
}
