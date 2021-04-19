package com.inthebytes.searchservice.entity;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="restaurant")
public class Restaurant {
	private Long restaurantId;
	private Long locationId;
	private String name;
	private String cuisine;

	@Id
	@Column(name = "restaurant_id", nullable = false)
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	public Long getRestaurantId() {
		return restaurantId;
	}

	public void setRestaurantId(Long restaurantId) {
		this.restaurantId = restaurantId;
	}

	@Basic
	@Column(name = "location_id", nullable = false)
	public Long getLocationId() {
		return locationId;
	}

	public void setLocationId(Long locationId) {
		this.locationId = locationId;
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
	@Column(name = "cuisine", nullable = false, length = 45)
	public String getCuisine() {
		return cuisine;
	}

	public void setCuisine(String cuisine) {
		this.cuisine = cuisine;
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
	public int hashCode() {
		int result = restaurantId != null ? restaurantId.hashCode() : 0;
		result = 31 * result + (locationId != null ? locationId.hashCode() : 0);
		result = 31 * result + (name != null ? name.hashCode() : 0);
		result = 31 * result + (cuisine != null ? cuisine.hashCode() : 0);
		return result;
	}

}