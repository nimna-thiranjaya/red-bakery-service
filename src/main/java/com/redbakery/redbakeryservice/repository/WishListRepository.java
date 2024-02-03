package com.redbakery.redbakeryservice.repository;

import com.redbakery.redbakeryservice.model.WishList;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WishListRepository extends JpaRepository<WishList, Long> {
}
