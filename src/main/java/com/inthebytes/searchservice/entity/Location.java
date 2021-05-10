package com.inthebytes.searchservice.entity;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Location {

	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	@Column(name = "location_id", nullable = false)
	private Long locationId;

	@Basic
	@Column(name = "street", nullable = false, length = 45)
	private String street;

	@Basic
	@Column(name = "unit", nullable = false, length = 45)
	private String unit;

	@Basic
	@Column(name = "city", nullable = false, length = 45)
	private String city;

	@Basic
	@Column(name = "state", nullable = false, length = 45)
	private String state;

	@Basic
	@Column(name = "zip_code", nullable = false)
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
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Location location = (Location) o;

		if (locationId != null ? !locationId.equals(location.locationId) : location.locationId != null) return false;
		if (street != null ? !street.equals(location.street) : location.street != null) return false;
		if (unit != null ? !unit.equals(location.unit) : location.unit != null) return false;
		if (city != null ? !city.equals(location.city) : location.city != null) return false;
		if (state != null ? !state.equals(location.state) : location.state != null) return false;
		if (zipCode != null ? !zipCode.equals(location.zipCode) : location.zipCode != null) return false;

		return true;
	}

	@Override
	public int hashCode() {
		int result = locationId != null ? locationId.hashCode() : 0;
		result = 31 * result + (street != null ? street.hashCode() : 0);
		result = 31 * result + (unit != null ? unit.hashCode() : 0);
		result = 31 * result + (city != null ? city.hashCode() : 0);
		result = 31 * result + (state != null ? state.hashCode() : 0);
		result = 31 * result + (zipCode != null ? zipCode.hashCode() : 0);
		return result;
	}
}
