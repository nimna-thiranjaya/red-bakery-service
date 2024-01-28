package com.redbakery.redbakeryservice.repository;

import com.redbakery.redbakeryservice.model.FoodCategory;
import com.redbakery.redbakeryservice.model.FoodType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FoodTypeRepository extends JpaRepository<FoodType, Long> {
    Boolean existsByFoodTypeNameAndFoodCategoryAndStatusNot(String foodTypeName, FoodCategory foodCategory, Integer status);

    List<FoodType> findAllByStatusIn(List<Integer> statusList);

    FoodType findByFoodTypeIdAndStatusIn(Long foodTypeId, List<Integer> statusList);

    List<FoodType> findAllByFoodCategoryAndStatusIn(FoodCategory foodCategory, List<Integer> statusList);
}
