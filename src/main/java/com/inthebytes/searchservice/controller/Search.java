package com.inthebytes.searchservice.controller;

import com.inthebytes.searchservice.service.FoodSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(value = "http://localhost:3000")
@RequestMapping(value = "search")
public class Search {

	@Autowired
	FoodSearchService foodSearchService;

	@GetMapping(value = "food", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
	public ResponseEntity<?> sortFood(@RequestParam(value = "sort", required = false) String sortOption, @RequestParam(value = "page", required = false, defaultValue = "1") String pageNumber) {
		if (sortOption == null) {
			return new ResponseEntity<>(foodSearchService.viewFoods(), HttpStatus.OK);
		}

		try {
			Integer page = Integer.parseInt(pageNumber) - 1;

			if (page < 0) {
				page = 0;
			}

			switch (sortOption) {
				case "low":
					return new ResponseEntity<>(foodSearchService.sortFoods("price", true, page), HttpStatus.OK);
				case "high":
					return new ResponseEntity<>(foodSearchService.sortFoods("price", false, page), HttpStatus.OK);
				case "star":
					return new ResponseEntity<>(foodSearchService.sortFoods("star", false, page), HttpStatus.OK);
				default:
					return new ResponseEntity<>(foodSearchService.viewFoods(), HttpStatus.OK);
			}
		} catch (NumberFormatException e) {
			return new ResponseEntity<>("Page must be a number", HttpStatus.BAD_REQUEST);
		}
	}
}
