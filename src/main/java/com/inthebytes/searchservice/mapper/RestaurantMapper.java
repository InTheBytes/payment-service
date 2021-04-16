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
	
	public Restaurant convert(RestaurantDTO dto) {
		Restaurant entity = new Restaurant();
		
		if (dto.getRestaurantId() != null)
			entity.setRestaurantId(dto.getRestaurantId());
		entity.setName(dto.getName());
		entity.setCuisine(dto.getCuisine());
		entity.setLocation(convert(dto.getLocation()));
		
		List<Food> foods = new ArrayList<Food>();
		if (dto.getFoods() == null)
			return entity;
		for (FoodDTO food : dto.getFoods()) {
			Food f = convert(food);
			f.setRestaurant(entity);
			foods.add(f);
		}
		entity.setFoods(foods);
		
		return entity;
	}
	
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
	
	public Location convert(LocationDTO dto) {
		Location entity = new Location();
		
		if (dto.getLocationId() != null)
			entity.setLocationId(dto.getLocationId());
		entity.setUnit(dto.getUnit());
		entity.setStreet(dto.getStreet());
		entity.setCity(dto.getCity());
		entity.setState(dto.getState());
		entity.setZipCode(dto.getZipCode());
		
		return entity;
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
	
	public Food convert(FoodDTO dto) {
		Food entity = new Food();
		
		if (dto.getFoodId() != null)
			entity.setFoodId(dto.getFoodId());
		entity.setName(dto.getName());
		entity.setDescription(dto.getDescription());
		entity.setPrice(dto.getPrice());
		
		return entity;
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
