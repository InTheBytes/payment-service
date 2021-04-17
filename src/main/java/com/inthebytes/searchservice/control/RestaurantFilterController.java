package com.inthebytes.searchservice.control;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.inthebytes.searchservice.dto.RestaurantDTO;

@RestController
@RequestMapping("/restaurants/filter")
public class RestaurantFilterController {
	
	@GetMapping(value="", params = {"zip-code", "city", "cuisine", "price-max", "price-min"})
	public ResponseEntity<List<RestaurantDTO>> filterByZip(
			@RequestParam(value = "zip-code", required = false) Integer zip,
			@RequestParam(value = "city", required = false) String city,
			@RequestParam(value = "cuisine", required = false) String cuisine,
			@RequestParam(value = "price-max", required = false) Double priceMax,
			@RequestParam(value = "price-min", required = false) Double priceMin) {
		
		return null;
	}
}
