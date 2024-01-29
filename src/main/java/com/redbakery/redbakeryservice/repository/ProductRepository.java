package com.redbakery.redbakeryservice.repository;

import com.redbakery.redbakeryservice.model.FoodType;
import com.redbakery.redbakeryservice.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.List;

@EnableJpaRepositories
@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findAllByStatusIn(List<Integer> status);

    Product findByProductIdAndStatusIn(Long productId, List<Integer> status);

    List<Product> findAllByFoodTypeAndStatusIn(FoodType foodType, List<Integer> value);

    List<Product> findAllByProductNameContainingIgnoreCaseAndStatusIn(String name, List<Integer> value);
}
