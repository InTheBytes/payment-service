package com.inthebytes.searchservice.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PostLoad;
import javax.persistence.Transient;

@Entity
@Table(name = "restaurant")
public class Restaurant implements Serializable {

	private static final long serialVersionUID = -8756584311354409044L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "restaurant_id", nullable = false)
	private Long restaurantId;

	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "location_id")
	private Location location;

	@Column(name = "name", nullable = false, length = 45)
	private String name;

	@Column(name = "cuisine", nullable = false, length = 45)
	private String cuisine;

	@OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	private List<Food> foods;

	@Transient
	private Double price;

	public Long getRestaurantId() {
		return restaurantId;
	}
	public void setRestaurantId(Long restaurantId) {
		this.restaurantId = restaurantId;
	}

	public Location getLocation() {
		return location;
	}
	public void setLocation(Location location) {
		this.location = location;
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

	public List<Food> getFoods() {
		return foods;
	}

	public void setFoods(List<Food> foods) {
		this.foods = foods;
	}

	public Double getPrice() {
		return price;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public int hashCode() {

		int result = restaurantId != null ? restaurantId.hashCode() : 0;
		result = 31 * result + (location != null ? location.hashCode() : 0);
		result = 31 * result + (name != null ? name.hashCode() : 0);
		result = 31 * result + (cuisine != null ? cuisine.hashCode() : 0);
		return result;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Restaurant restaurant = (Restaurant) o;

		if (restaurantId != null ? !restaurantId.equals(restaurant.restaurantId) : restaurant.restaurantId != null) return false;

		return true;
	}

	@Override
	public String toString() {
		return "Restaurant [restaurantId=" + restaurantId + ", location=" + location + ", name=" + name + ", cuisine="
				+ cuisine + ", foods=" + foods + ", price=" + price + "]";
	}

	private Double meanPrice() {
		Double sum = foods.stream()
				.mapToDouble(x -> x.getPrice())
				.sum();
		return sum/foods.size();
	}

	private Double modePrice() {
		List<Double> prices = foods.stream()
				.filter(x -> (x != null && x.getPrice() != null))
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

	@PostLoad
	public void representativePrice() {
		if (foods.size() == 0)
			this.price = 0.0;
		Double mode = modePrice();
		Integer frequency = 0;
		for (Food food : foods) 
			if (food.getPrice().equals(mode))
				++frequency;

		if (frequency >= foods.size()/4)
			this.price = mode;
		else
			this.price = meanPrice();
	}
}
