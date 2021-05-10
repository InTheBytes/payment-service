package com.inthebytes.searchservice.dao;

import org.springframework.stereotype.Repository;

import com.inthebytes.searchservice.entity.Food;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

@Repository
public interface FoodDao extends JpaRepository<Food, Long>, JpaSpecificationExecutor<Food> {
	List<Food> findByNameContaining(String query);
}

