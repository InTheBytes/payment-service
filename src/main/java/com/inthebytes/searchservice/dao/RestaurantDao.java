package com.inthebytes.searchservice.dao;


import org.springframework.stereotype.Repository;

import com.inthebytes.searchservice.entity.Restaurant;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface RestaurantDao extends JpaRepository<Restaurant, Long> {
	List<Restaurant> findByNameContaining(String query);
}