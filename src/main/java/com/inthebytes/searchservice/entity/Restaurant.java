package com.inthebytes.searchservice.entity;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PostLoad;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "restaurant")
public class Restaurant implements Serializable {

	private static final long serialVersionUID = -8756584311354409044L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "restaurant_id")
	private Long restaurantId;

	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "location_id")
	private Location location;

	@Column(name = "name")
	private String name;

	@Column(name = "cuisine")
	private String cuisine;

	@OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL, orphanRemoval = true)
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
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cuisine == null) ? 0 : cuisine.hashCode());
		result = prime * result + ((foods == null) ? 0 : foods.hashCode());
		result = prime * result + ((location == null) ? 0 : location.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((price == null) ? 0 : price.hashCode());
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
		Restaurant other = (Restaurant) obj;
		if (cuisine == null) {
			if (other.cuisine != null)
				return false;
		} else if (!cuisine.equals(other.cuisine))
			return false;
		if (foods == null) {
			if (other.foods != null)
				return false;
		} else if (!foods.equals(other.foods))
			return false;
		if (location == null) {
			if (other.location != null)
				return false;
		} else if (!location.equals(other.location))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (price == null) {
			if (other.price != null)
				return false;
		} else if (!price.equals(other.price))
			return false;
		if (restaurantId == null) {
			if (other.restaurantId != null)
				return false;
		} else if (!restaurantId.equals(other.restaurantId))
			return false;
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
