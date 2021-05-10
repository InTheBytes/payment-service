package com.inthebytes.searchservice.dao;

import com.inthebytes.searchservice.projection.FoodProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import com.inthebytes.searchservice.entity.Food;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface FoodDao extends JpaRepository<Food, Long> {
	/*@Query(value = "SELECT f FROM Food ORDER BY ")
	List<FoodProjection> findAllOrderByRestaurantStars();*/
}
