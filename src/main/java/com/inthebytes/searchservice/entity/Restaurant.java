package com.inthebytes.searchservice.entity;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Restaurant {

	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	@Column(name = "restaurant_id", nullable = false)
	private Long restaurantId;

	@Basic
	@Column(name = "name", nullable = false, length = 45)
	private String name;

	@Basic
	@Column(name = "cuisine", nullable = false, length = 45)
	private String cuisine;

	@ManyToOne
	@JoinColumn(name = "location_id", referencedColumnName = "location_id", nullable = false)
	private Location location;

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

	public Location getLocation() {
		return location;
	}
	public void setLocation(Location location) {
		this.location = location;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Restaurant that = (Restaurant) o;

		if (restaurantId != null ? !restaurantId.equals(that.restaurantId) : that.restaurantId != null) return false;
		if (name != null ? !name.equals(that.name) : that.name != null) return false;
		if (cuisine != null ? !cuisine.equals(that.cuisine) : that.cuisine != null) return false;

		return true;
	}

	@Override
	public int hashCode() {
		int result = restaurantId != null ? restaurantId.hashCode() : 0;
		result = 31 * result + (name != null ? name.hashCode() : 0);
		result = 31 * result + (cuisine != null ? cuisine.hashCode() : 0);
		return result;
	}
}
