package com.inthebytes.searchservice.service;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.inthebytes.searchservice.dao.FoodDao;
import com.inthebytes.searchservice.dao.RestaurantDao;
import com.inthebytes.searchservice.dto.FoodDTO;
import com.inthebytes.searchservice.dto.RestaurantDTO;
import com.inthebytes.searchservice.entity.Food;
import com.inthebytes.searchservice.entity.Restaurant;
import com.inthebytes.searchservice.mapper.RestaurantMapper;

@Service
public class SearchService {
	
	@Autowired
	FoodDao foodRepo;
	
	@Autowired
	RestaurantDao restaurantRepo;
	
	@Autowired
	RestaurantMapper mapper;
	
	public List<FoodDTO> foodSearch(String query) throws SQLException {
		List<Food> result = foodRepo.findByNameContaining(query);
		System.out.println(result);
		return result.stream().map((x) -> mapper.convert(x)).collect(Collectors.toList());
	}
	
	public List<RestaurantDTO> restaurantSearch(String query) throws SQLException {
		List<Restaurant> result = restaurantRepo.findByNameContaining(query);
		return result.stream().map((x) -> mapper.convert(x)).collect(Collectors.toList());
	}
}