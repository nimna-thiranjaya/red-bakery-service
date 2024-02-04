package com.redbakery.redbakeryservice.repository;

import com.redbakery.redbakeryservice.model.Cart;
import com.redbakery.redbakeryservice.model.CartDetail;
import com.redbakery.redbakeryservice.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartDetailRepository extends JpaRepository<CartDetail, Long> {
    List<CartDetail> findAllByCartAndProductAndStatusIn(Cart cart, Product product, List<Integer> status);
    List<CartDetail> findAllByCartAndStatusIn(Cart cart, List<Integer> status);
    CartDetail findByCartDetailsIdAndStatusIn(Long cartDetailId, List<Integer> status);
}
