package com.inthebytes.searchservice.service;

import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.inthebytes.searchservice.dto.RestaurantDTO;
import com.inthebytes.searchservice.mapper.RestaurantMapper;
import com.inthebytes.searchservice.repository.RestaurantRepository;

@Service
public class RestaurantFilterService {
	
	@Autowired
	RestaurantRepository repo;
	
	@Autowired
	RestaurantMapper mapper;
	
	
	public List<RestaurantDTO> filterByCuisine(String cuisine) {
		return filter((x) -> (cuisine.equals(x.getCuisine()) ? x : null));
	}
	
	public List<RestaurantDTO> filterByCuisine(List<RestaurantDTO> results, String cuisine) {
		return filterResults(results, (x) -> (cuisine.equals(x.getCuisine()) ? x : null));
	}
	
	public List<RestaurantDTO> filterByCity(String city) {
		return filter((x) -> (city.equals(x.getLocation().getCity()) ? x : null));
	}
	
	public List<RestaurantDTO> filterByCity(List<RestaurantDTO> results, String city) {
		return filterResults(results, (x) -> (city.equals(x.getLocation().getCity()) ? x : null));
	}
	
	public List<RestaurantDTO> filterByZip(Integer zipCode) {
		return filter((x) -> (zipCode.equals(x.getLocation().getZipCode()) ? x : null));
	}
	
	public List<RestaurantDTO> filterByZip(List<RestaurantDTO> results, Integer zipCode) {
		return filterResults(results, (x) -> (zipCode.equals(x.getLocation().getZipCode()) ? x : null));
	}
	
	public List<RestaurantDTO> lessThanPrice(Double price) {
		return filter((x) -> (x.getPrice() < price) ? x : null);
	}
	
	public List<RestaurantDTO> lessThanPrice(List<RestaurantDTO> results, Double price) {
		return filterResults(results, (x) -> (x.getPrice() < price) ? x : null);
	}
	
	public List<RestaurantDTO> greaterThanPrice(Double price) {
		return filter((x) -> (x.getPrice() > price) ? x : null);
	}
	
	public List<RestaurantDTO> greaterThanPrice(List<RestaurantDTO> results, Double price) {
		return filterResults(results, (x) -> (x.getPrice() > price) ? x : null);
	}
	
	public List<RestaurantDTO> inPriceBracket(Double lowPrice, Double highPrice) {
		return filter((x) -> (x.getPrice() >= lowPrice && x.getPrice() <= highPrice) ? x : null);
	}
	
	public List<RestaurantDTO> inPriceBracket(List<RestaurantDTO> results, Double lowPrice, Double highPrice) {
		return filterResults(results, (x) -> (x.getPrice() >= lowPrice && x.getPrice() <= highPrice) ? x : null);
	}
	
	private List<RestaurantDTO> filterResults(List<RestaurantDTO> results, 
			Function<? super RestaurantDTO, ? extends RestaurantDTO> mapOp) {
		return results.stream()
				.map(mapOp)
				.filter(Objects::nonNull)
				.collect(Collectors.toList());
	}
	
	private List<RestaurantDTO> filter(Function<? super RestaurantDTO, ? extends RestaurantDTO> mapOp) {
		return repo.findAll()
				.stream()
				.map((x) -> mapper.convert(x))
				.map(mapOp)
				.filter(Objects::nonNull)
				.collect(Collectors.toList());
	}

}
