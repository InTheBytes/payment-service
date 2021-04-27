package com.inthebytes.searchservice.service;

import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.inthebytes.searchservice.dao.RestaurantDao;
import com.inthebytes.searchservice.dto.RestaurantDTO;
import com.inthebytes.searchservice.mapper.RestaurantMapper;

@Service
public class RestaurantFilterService {
	
	@Autowired
	RestaurantDao repo;
	
	@Autowired
	RestaurantMapper mapper;
	
	public List<RestaurantDTO> layerFilters(List<RestaurantDTO> results, 
			Integer zip, String city, String cuisine, Double priceMax, Double priceMin) {
		if (zip != 0)
			results = filterByZip(results, zip);
		if (!"_".equals(city))
			results = filterByCity(results, city);
		if (!"_".equals(cuisine))
			results = filterByCuisine(results, cuisine);
		if (priceMax != 0.0 && priceMin != 0.0)
			results = inPriceBracket(results, priceMin, priceMax);
		else {
			if (priceMax != 0.0)
				results = lessThanPrice(results, priceMax);
			if (priceMin != 0.0)
				results = greaterThanPrice(results, priceMin); 
		}
		return results;
	}
	
	public List<RestaurantDTO> applyFilters(Integer zip, String city, String cuisine, Double priceMax, Double priceMin) {
		List<RestaurantDTO> results = null;
		if (zip != 0)
			results = filterByZip(zip);
		if (!"_".equals(city))
			results = (results == null) ? 
					filterByCity(city) : filterByCity(results, city);
		if (!"_".equals(cuisine))
			results = (results == null) ? 
					filterByCuisine(cuisine) : filterByCuisine(results, cuisine);
		if (priceMax != 0.0 && priceMin != 0.0)
			results = (results == null) ? 
					inPriceBracket(priceMin, priceMax) : inPriceBracket(results, priceMin, priceMax);
		else {
			if (priceMax != 0.0)
				results = (results == null) ?
						lessThanPrice(priceMax) : lessThanPrice(results, priceMax);
			if (priceMin != 0.0)
				results = (results == null) ?
						greaterThanPrice(priceMin) : greaterThanPrice(results, priceMin); 
		}
		return results;
			
	}
	
	
	private List<RestaurantDTO> filterByCuisine(String cuisine) {
		return filter((x) -> (cuisine.equals(x.getCuisine()) ? x : null));
	}
	
	private List<RestaurantDTO> filterByCuisine(List<RestaurantDTO> results, String cuisine) {
		return filterResults(results, (x) -> (cuisine.equals(x.getCuisine()) ? x : null));
	}
	
	private List<RestaurantDTO> filterByCity(String city) {
		return filter((x) -> (city.equals(x.getLocation().getCity()) ? x : null));
	}
	
	private List<RestaurantDTO> filterByCity(List<RestaurantDTO> results, String city) {
		return filterResults(results, (x) -> (city.equals(x.getLocation().getCity()) ? x : null));
	}
	
	private List<RestaurantDTO> filterByZip(Integer zipCode) {
		return filter((x) -> (zipCode.equals(x.getLocation().getZipCode()) ? x : null));
	}
	
	private List<RestaurantDTO> filterByZip(List<RestaurantDTO> results, Integer zipCode) {
		return filterResults(results, (x) -> (zipCode.equals(x.getLocation().getZipCode()) ? x : null));
	}
	
	private List<RestaurantDTO> lessThanPrice(Double price) {
		return filter((x) -> (x.getPrice() < price) ? x : null);
	}
	
	private List<RestaurantDTO> lessThanPrice(List<RestaurantDTO> results, Double price) {
		return filterResults(results, (x) -> (x.getPrice() < price) ? x : null);
	}
	
	private List<RestaurantDTO> greaterThanPrice(Double price) {
		return filter((x) -> (x.getPrice() > price) ? x : null);
	}
	
	private List<RestaurantDTO> greaterThanPrice(List<RestaurantDTO> results, Double price) {
		return filterResults(results, (x) -> (x.getPrice() > price) ? x : null);
	}
	
	private List<RestaurantDTO> inPriceBracket(Double lowPrice, Double highPrice) {
		return filter((x) -> (x.getPrice() >= lowPrice && x.getPrice() <= highPrice) ? x : null);
	}
	
	private List<RestaurantDTO> inPriceBracket(List<RestaurantDTO> results, Double lowPrice, Double highPrice) {
		return filterResults(results, (x) -> (x.getPrice() >= lowPrice && x.getPrice() <= highPrice) ? x : null);
	}
	
	private List<RestaurantDTO> filterResults(List<RestaurantDTO> results, 
			Function<? super RestaurantDTO, ? extends RestaurantDTO> mapOp) {
		List<RestaurantDTO> result = results.stream()
				.map(mapOp)
				.filter(Objects::nonNull)
				.collect(Collectors.toList());
		if (result.size() > 0)
			return result;
		else
			return null;
	}
	
	private List<RestaurantDTO> filter(Function<? super RestaurantDTO, ? extends RestaurantDTO> mapOp) {
		List<RestaurantDTO> results =  repo.findAll()
				.stream()
				.map((x) -> mapper.convert(x))
				.map(mapOp)
				.filter(Objects::nonNull)
				.collect(Collectors.toList());
		if (results.size() > 0)
			return results;
		else
			return null;
	}

}
