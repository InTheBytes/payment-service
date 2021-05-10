package com.inthebytes.searchservice.entity;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.xml.bind.annotation.XmlRootElement;
import java.math.BigDecimal;

@Entity
public class Food {

	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	@Column(name = "food_id", nullable = false)
	private Long foodId;

	@Basic
	@Column(name = "name", nullable = false, length = 45)
	private String name;

	@Basic
	@Column(name = "price", nullable = false, precision = 0)
	private BigDecimal price;

	@Basic
	@Column(name = "description", nullable = false, length = 100)
	private String description;

	@ManyToOne
	@JoinColumn(name = "restaurant_id", referencedColumnName = "restaurant_id", nullable = false)
	private Restaurant restaurant;

	public Long getFoodId() {
		return foodId;
	}
	public void setFoodId(Long foodId) {
		this.foodId = foodId;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}

	public Restaurant getRestaurant() {
		return restaurant;
	}
	public void setRestaurant(Restaurant restaurant) {
		this.restaurant = restaurant;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Food food = (Food) o;

		if (foodId != null ? !foodId.equals(food.foodId) : food.foodId != null) return false;
		if (name != null ? !name.equals(food.name) : food.name != null) return false;
		if (price != null ? !price.equals(food.price) : food.price != null) return false;
		if (description != null ? !description.equals(food.description) : food.description != null) return false;

		return true;
	}

	@Override
	public int hashCode() {
		int result = foodId != null ? foodId.hashCode() : 0;
		result = 31 * result + (name != null ? name.hashCode() : 0);
		result = 31 * result + (price != null ? price.hashCode() : 0);
		result = 31 * result + (description != null ? description.hashCode() : 0);
		return result;
	}
}
