package com.inthebytes.searchservice.dto;

import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.Id;
import javax.persistence.PostLoad;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.inthebytes.searchservice.entity.Food;

public class RestaurantDTO {
	
	public RestaurantDTO(String name, String cuisine, LocationDTO location) {
		super();
		this.name = name;
		this.cuisine = cuisine;
		this.location = location;
	}

	@Id
	@Nullable
	@JsonIgnore
	private Long restaurantId;

	@NonNull
	private String name;

	@NonNull
	private String cuisine;
	
	@NonNull
	private LocationDTO location;

	@Nullable
//	@JsonIgnore
	private List<FoodDTO> foods;

	public Long getRestaurantId() {
		return restaurantId;
	}

	public void setRestaurantId(Long restaurantId) {
		this.restaurantId = restaurantId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCuisine() {
		return cuisine;
	}

	public void setCuisine(String cuisine) {
		this.cuisine = cuisine;
	}

	public LocationDTO getLocation() {
		return location;
	}

	public void setLocation(LocationDTO location) {
		this.location = location;
	}

	public List<FoodDTO> getFoods() {
		return foods;
	}

	public void setFoods(List<FoodDTO> foods) {
		this.foods = foods;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((restaurantId == null) ? 0 : restaurantId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RestaurantDTO other = (RestaurantDTO) obj;
		if (restaurantId == null) {
			if (other.restaurantId != null)
				return false;
		} else if (!restaurantId.equals(other.restaurantId))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "RestaurantDTO [name=" + name + ", cuisine=" + cuisine + ", location=" + location + ", foods=" + foods
				+ ", price=" + getPrice() + "]";
	}
	
	private Double meanPrice() {
		Double sum = foods.stream()
				.mapToDouble(x -> x.getPrice())
				.sum();
		return sum/foods.size();
	}
	
	private Double modePrice() {
		List<Double> prices = foods.stream()
				.mapToDouble(x -> x.getPrice())
				.boxed().collect(Collectors.toList());
		Double modeValue = null;
		Integer maxCount = 0;
		for (Double price : prices) {
			Integer count = 0;
			for (Double check : prices) {
				if (check.equals(price))
					++count;
			}
			if (count > maxCount) {
				maxCount = count;
				modeValue = price;
			}
		}
		return modeValue;
	}
	
	public Double getPrice() {
		if (foods.size() == 0)
			return 0.0;
		Double mode = modePrice();
		Integer frequency = 0;
		for (FoodDTO food : foods) 
			if (food.getPrice().equals(mode))
				++frequency;
		
		if (frequency >= foods.size()/4)
			return mode;
		else
			return meanPrice();
	}
}
