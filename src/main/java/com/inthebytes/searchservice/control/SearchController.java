package com.inthebytes.searchservice.control;

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

import com.inthebytes.searchservice.dto.FoodDTO;
import com.inthebytes.searchservice.dto.RestaurantDTO;
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
	public ResponseEntity<List<FoodDTO>> foodSearch(@RequestBody String query) {
		List<FoodDTO> result;
		ResponseEntity<List<FoodDTO>> response;
		try {
			result = service.foodSearch(query);
			response = new ResponseEntity<List<FoodDTO>>(result, HttpStatus.ACCEPTED);
		} catch (SQLException e) {
			response = new ResponseEntity<List<FoodDTO>>(HttpStatus.BAD_REQUEST);
			e.printStackTrace();
		}
		return response;
	}
	
	@RequestMapping(path = "/restaurant", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<List<RestaurantDTO>> restaurantSearch(@RequestBody String query) {
		List<RestaurantDTO> result;
		ResponseEntity<List<RestaurantDTO>> response;
		try {
			result = service.restaurantSearch(query);
			response = new ResponseEntity<List<RestaurantDTO>>(result, HttpStatus.ACCEPTED);
		} catch (SQLException e) {
			response = new ResponseEntity<List<RestaurantDTO>>(HttpStatus.BAD_REQUEST);
			e.printStackTrace();
		}
		return response;
	}
}