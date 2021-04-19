package com.inthebytes.searchservice.service;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.inthebytes.searchservice.dao.FoodDao;
import com.inthebytes.searchservice.dao.RestaurantDao;
import com.inthebytes.searchservice.entity.Food;
import com.inthebytes.searchservice.entity.Restaurant;

@Service
public class SearchService {
	
	@Autowired
	FoodDao foodRepo;
	
	@Autowired
	RestaurantDao restaurantRepo;

	public List<Food> foodSearch(String query) throws SQLException {
		List<Food> result = foodRepo.findByNameContaining(query);
		return result;
	}
	
	public List<Restaurant> restaurantSearch(String query) throws SQLException {
		List<Restaurant> result = restaurantRepo.findByNameContaining(query);
		return result;
	}
}