package com.inthebytes.searchservice.projection;

import java.math.BigDecimal;

public interface FoodProjection {
	Long getFoodId();
	String getName();
	BigDecimal getPrice();
	String getDescription();

	interface RestaurantProjection {
		Long getRestaurantId();
		String getName();
		String getCuisine();
	}
}
