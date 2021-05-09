package com.inthebytes.searchservice.dto;

import javax.persistence.Id;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class LocationDTO {

	public LocationDTO(String street, String unit, String city, String state, Integer zipCode) {
		super();
		this.street = street;
		this.unit = unit;
		this.city = city;
		this.state = state;
		this.zipCode = zipCode;
	}

	@Id
	@Nullable
	@JsonIgnore
	private Long locationId;
	
	@NonNull
	private String street;
	
	@NonNull
	private String unit;
	
	@NonNull
	private String city;
	
	@NonNull
	private String state;
	
	@NonNull
	private Integer zipCode;

	public Long getLocationId() {
		return locationId;
	}

	public void setLocationId(Long locationId) {
		this.locationId = locationId;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public Integer getZipCode() {
		return zipCode;
	}

	public void setZipCode(Integer zipCode) {
		this.zipCode = zipCode;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((locationId == null) ? 0 : locationId.hashCode());
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
		LocationDTO other = (LocationDTO) obj;
		if (locationId == null) {
			if (other.locationId != null)
				return false;
		} else if (!locationId.equals(other.locationId))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "LocationDTO [street=" + street + ", unit=" + unit + ", city=" + city + ", state=" + state + ", zipCode="
				+ zipCode + "]";
	}
}
