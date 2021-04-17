package com.inthebytes.searchservice.control;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.inthebytes.searchservice.dto.RestaurantDTO;
import com.inthebytes.searchservice.service.RestaurantFilterService;

@RestController
@RequestMapping("/restaurants/filter")
public class RestaurantFilterController {
	
	@Autowired
	RestaurantFilterService service;
	
	@GetMapping(value="")
	public ResponseEntity<List<RestaurantDTO>> filter(
			@RequestParam(value = "zip-code", required = false, defaultValue = "0") Integer zip,
			@RequestParam(value = "city", required = false, defaultValue = "_") String city,
			@RequestParam(value = "cuisine", required = false, defaultValue = "_") String cuisine,
			@RequestParam(value = "price-max", required = false, defaultValue = "0.0") Double priceMax,
			@RequestParam(value = "price-min", required = false, defaultValue = "0.0") Double priceMin) {
		
		List<RestaurantDTO> results = applyFilters(zip, city, cuisine, priceMax, priceMin);
		if (results == null) 
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		else
			return ResponseEntity.ok().body(results);
	}
	
	@PostMapping(value="")
	public ResponseEntity<List<RestaurantDTO>> filterResults(@Valid @RequestBody List<RestaurantDTO> results,
			@RequestParam(value = "zip-code", required = false, defaultValue = "0") Integer zip,
			@RequestParam(value = "city", required = false, defaultValue = "_") String city,
			@RequestParam(value = "cuisine", required = false, defaultValue = "_") String cuisine,
			@RequestParam(value = "price-max", required = false, defaultValue = "0.0") Double priceMax,
			@RequestParam(value = "price-min", required = false, defaultValue = "0.0") Double priceMin) {
		
		List<RestaurantDTO> filtered = layerFilters(results, zip, city, cuisine, priceMax, priceMin);
		if (filtered == null || filtered.size() == 0)
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		else
			return ResponseEntity.ok().body(filtered);
	}
	
	private List<RestaurantDTO> layerFilters(List<RestaurantDTO> results, 
			Integer zip, String city, String cuisine, Double priceMax, Double priceMin) {
		if (zip != 0)
			results = service.filterByZip(results, zip);
		if (!"_".equals(city))
			results = service.filterByCity(results, city);
		if (!"_".equals(cuisine))
			results = service.filterByCuisine(results, cuisine);
		if (priceMax != 0.0 && priceMin != 0.0)
			results = service.inPriceBracket(results, priceMin, priceMax);
		else {
			if (priceMax != 0.0)
				results = service.lessThanPrice(results, priceMax);
			if (priceMin != 0.0)
				results = service.greaterThanPrice(results, priceMin); 
		}
		return results;
	}
	
	private List<RestaurantDTO> applyFilters(Integer zip, String city, String cuisine, Double priceMax, Double priceMin) {
		List<RestaurantDTO> results = null;
		if (zip != 0)
			results = service.filterByZip(zip);
		if (!"_".equals(city))
			results = (results == null) ? 
					service.filterByCity(city) : service.filterByCity(results, city);
		if (!"_".equals(cuisine))
			results = (results == null) ? 
					service.filterByCuisine(cuisine) : service.filterByCuisine(results, cuisine);
		if (priceMax != 0.0 && priceMin != 0.0)
			results = (results == null) ? 
					service.inPriceBracket(priceMin, priceMax) : service.inPriceBracket(results, priceMin, priceMax);
		else {
			if (priceMax != 0.0)
				results = (results == null) ?
						service.lessThanPrice(priceMax) : service.lessThanPrice(results, priceMax);
			if (priceMin != 0.0)
				results = (results == null) ?
						service.greaterThanPrice(priceMin) : service.greaterThanPrice(results, priceMin); 
		}
		return results;
			
	}
}
