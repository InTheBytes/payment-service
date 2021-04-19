package com.inthebytes.searchservice.entity;


import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import java.math.BigDecimal;

@Entity
@Table(name="food")
public class Food {
	private Long foodId;
	private Long restaurantId;
	private String name;
	private BigDecimal price;
	private String description;

	@Id
	@Column(name = "food_id", nullable = false)
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	public Long getFoodId() {
		return foodId;
	}

	public void setFoodId(Long foodId) {
		this.foodId = foodId;
	}

	@Basic
	@Column(name = "restaurant_id", nullable = false)
	public Long getRestaurantId() {
		return restaurantId;
	}

	public void setRestaurantId(Long restaurantId) {
		this.restaurantId = restaurantId;
	}

	@Basic
	@Column(name = "name", nullable = false, length = 45)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Basic
	@Column(name = "price", nullable = false)
	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	@Basic
	@Column(name = "description", nullable = false, length = 100)
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Food food = (Food) o;

		if (foodId != null ? !foodId.equals(food.foodId) : food.foodId != null) return false;

		return true;
	}

	@Override
	public int hashCode() {
		int result = foodId != null ? foodId.hashCode() : 0;
		result = 31 * result + (restaurantId != null ? restaurantId.hashCode() : 0);
		result = 31 * result + (name != null ? name.hashCode() : 0);
		result = 31 * result + (price != null ? price.hashCode() : 0);
		result = 31 * result + (description != null ? description.hashCode() : 0);
		return result;
	}

}