package com.redbakery.redbakeryservice.repository;

import com.redbakery.redbakeryservice.model.Discount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DiscountRepository extends JpaRepository<Discount, Long> {
}
