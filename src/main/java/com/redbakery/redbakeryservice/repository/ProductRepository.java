package com.redbakery.redbakeryservice.repository;

import com.redbakery.redbakeryservice.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

@EnableJpaRepositories
@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
}
