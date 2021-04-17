package com.inthebytes.searchservice.mapper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.inthebytes.searchservice.dto.FoodDTO;
import com.inthebytes.searchservice.dto.LocationDTO;
import com.inthebytes.searchservice.dto.RestaurantDTO;
import com.inthebytes.searchservice.entity.Food;
import com.inthebytes.searchservice.entity.Location;
import com.inthebytes.searchservice.entity.Restaurant;

@Component
public class RestaurantMapper {
	
	public RestaurantDTO convert(Restaurant entity) {
		RestaurantDTO dto = new RestaurantDTO(
				entity.getName(), 
				entity.getCuisine(), 
				convert(entity.getLocation()));
		
		dto.setRestaurantId(entity.getRestaurantId());
		
		if (entity.getFoods() == null)
			return dto;
		List<FoodDTO> foods = new ArrayList<FoodDTO>();
		for (Food food : entity.getFoods()) {
			FoodDTO f = convert(food);
			f.setRestaurant(dto);
			foods.add(f);
		}
		dto.setFoods(foods);
		
		return dto;
	}
	
	public LocationDTO convert(Location entity) {
		LocationDTO dto = new LocationDTO(
				entity.getStreet(), 
				entity.getUnit(), 
				entity.getCity(), 
				entity.getState(), 
				entity.getZipCode());
		
		dto.setLocationId(entity.getLocationId());
		
		return dto;
	}
	
	public FoodDTO convert(Food entity) {
		FoodDTO dto = new FoodDTO(
				entity.getName(), 
				entity.getPrice(), 
				entity.getDescription());
		
		dto.setFoodId(entity.getFoodId());
		
		return dto;
	}

}
