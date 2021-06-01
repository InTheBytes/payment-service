package com.inthebytes.searchservice.dto;

import org.springframework.data.annotation.Id;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class FoodDTO {
	
	public FoodDTO(String name, Double price, String description) {
		super();
		this.name = name;
		this.price = price;
		this.description = description;
	}

	@Id
	@Nullable
	@JsonIgnore
	private String foodId;

	@NonNull
	private String name;
	
	@NonNull
	private Double price;
	
	@NonNull
	private String description;
	
	@Nullable
	@JsonIgnore
	private RestaurantDTO restaurant;

	public String getFoodId() {
		return foodId;
	}

	public void setFoodId(String foodId) {
		this.foodId = foodId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public RestaurantDTO getRestaurant() {
		return restaurant;
	}

	public void setRestaurant(RestaurantDTO restaurant) {
		this.restaurant = restaurant;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((foodId == null) ? 0 : foodId.hashCode());
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
		FoodDTO other = (FoodDTO) obj;
		if (foodId == null) {
			if (other.foodId != null)
				return false;
		} else if (!foodId.equals(other.foodId))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "FoodDTO [name=" + name + ", price=" + price + ", description=" + description + "]";
	}
}
