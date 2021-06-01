package com.inthebytes.searchservice.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.inthebytes.searchservice.entity.Food;


import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

@Repository
public interface FoodDao extends JpaRepository<Food, String>, JpaSpecificationExecutor<Food> {
	Page<Food> findByNameContaining(String query, Pageable pageable);
}
