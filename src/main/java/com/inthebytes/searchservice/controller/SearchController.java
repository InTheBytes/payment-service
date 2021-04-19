package com.inthebytes.searchservice.controller;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.inthebytes.searchservice.entity.Food;
import com.inthebytes.searchservice.entity.Restaurant;
import com.inthebytes.searchservice.service.SearchService;



@RestController
@RequestMapping("/search")
public class SearchController {

	@Autowired
	SearchService service;
	
	@RequestMapping(path = "/food", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<List<Food>> foodSearch(@RequestBody String query) {
		List<Food> result;
		ResponseEntity<List<Food>> response;
		try {
			result = service.foodSearch(query);
			response = new ResponseEntity<List<Food>>(result, HttpStatus.ACCEPTED);
		} catch (SQLException e) {
			response = new ResponseEntity<List<Food>>(HttpStatus.BAD_REQUEST);
			e.printStackTrace();
		}
		return response;
	}
	
	@RequestMapping(path = "/restaurant", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<List<Restaurant>> restaurantSearch(@RequestBody String query) {
		List<Restaurant> result;
		ResponseEntity<List<Restaurant>> response;
		try {
			result = service.restaurantSearch(query);
			response = new ResponseEntity<List<Restaurant>>(result, HttpStatus.ACCEPTED);
		} catch (SQLException e) {
			response = new ResponseEntity<List<Restaurant>>(HttpStatus.BAD_REQUEST);
			e.printStackTrace();
		}
		return response;
	}
}