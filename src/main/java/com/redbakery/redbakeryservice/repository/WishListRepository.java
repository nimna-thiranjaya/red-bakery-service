package com.redbakery.redbakeryservice.repository;

import com.redbakery.redbakeryservice.model.User;
import com.redbakery.redbakeryservice.model.WishList;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WishListRepository extends JpaRepository<WishList, Long> {
    List<WishList> findAllByUserAndStatusIn(User user, List<Integer> status);
}
