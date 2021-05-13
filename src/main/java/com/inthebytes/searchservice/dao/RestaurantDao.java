package com.inthebytes.searchservice.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.inthebytes.searchservice.entity.Restaurant;

import java.util.List;

@Repository
public interface RestaurantDao extends JpaRepository<Restaurant, Long>, JpaSpecificationExecutor<Restaurant> {
	Restaurant findByRestaurantId(Long id);
	Restaurant findByName(String name);
	Page<Restaurant> findByNameContaining(String query, Pageable pageable);
}
