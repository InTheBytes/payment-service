package com.inthebytes.searchservice.controller;

import java.sql.SQLException;
import java.util.List;

import com.inthebytes.searchservice.entity.Food;
import com.inthebytes.searchservice.entity.Restaurant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.inthebytes.searchservice.dto.FoodDTO;
import com.inthebytes.searchservice.dto.RestaurantDTO;
import com.inthebytes.searchservice.service.SearchService;

@RestController
@CrossOrigin(origins = {"http://localhost:4200", "http://localhost:3000",
		"http://stacklunch.com", "http://admin.stacklunch.com", 
		"http://driver.stacklunch.com", "http://manager.stacklunch.com"})
@RequestMapping("/search")
public class SearchController {

	@Autowired
	SearchService service;
	
	@RequestMapping(path = "/food", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<?> foodSearch(
			@RequestParam(value = "query") String query, 
			@RequestParam(value = "sort", required = false, defaultValue = "low") String sortOption, 
			@RequestParam(value = "filter", required = false, defaultValue = "") String[] filters, 
			@RequestParam(value = "page", required = false, defaultValue = "0") String pageNumber) {
		
		Page<FoodDTO> result;
		ResponseEntity<?> response;

		try {
			Integer page = Integer.parseInt(pageNumber) - 1;
			if (page < 0) {
				page = 0;
			}

			Boolean direction;
			String sort = "";

			switch (sortOption) {
				case "high":
					sort = "price";
					direction = false;
					break;
				case "low":
				default:
					sort = "price";
					direction = true;
			}

			result = service.foodSearch(query, sort, filters, direction, page);
			response = new ResponseEntity<>(result, HttpStatus.OK);
		} catch (NumberFormatException e) {
			return new ResponseEntity<>("Page must be a number", HttpStatus.BAD_REQUEST);
		} catch (SQLException e) {
			response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			e.printStackTrace();
		}

		return response;
	}
	
	@RequestMapping(path = "/restaurant", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<?> restaurantSearch(
			@RequestParam(value = "query") String query, 
			@RequestParam(value = "sort", required = false, defaultValue = "low") String sortOption, 
			@RequestParam(value = "filter", required = false, defaultValue = "") String[] filters, 
			@RequestParam(value = "page", required = false, defaultValue = "0") String pageNumber) {
		
		Page<RestaurantDTO> result;
		ResponseEntity<?> response;

		try {
			Integer page = Integer.parseInt(pageNumber) - 1;
			if (page < 0) {
				page = 0;
			}

			result = service.restaurantSearch(query, "name", filters, true, page);
			response = new ResponseEntity<>(result, HttpStatus.OK);
		} catch (NumberFormatException e) {
			return new ResponseEntity<>("Page must be a number", HttpStatus.BAD_REQUEST);
		} catch (SQLException e) {
			response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			e.printStackTrace();
		}

		return response;
	}
}