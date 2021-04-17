package com.inthebytes.searchservice.control;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.util.ArrayList;
import java.util.List;

import org.hamcrest.Matchers;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.bind.annotation.RequestParam;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.inthebytes.searchservice.dto.FoodDTO;
import com.inthebytes.searchservice.dto.LocationDTO;
import com.inthebytes.searchservice.dto.RestaurantDTO;
import com.inthebytes.searchservice.entity.Restaurant;
import com.inthebytes.searchservice.service.RestaurantFilterService;

@WebMvcTest(RestaurantFilterController.class)
@AutoConfigureMockMvc
@TestInstance(Lifecycle.PER_CLASS)
public class RestaurantFilterControllerTest {

	@MockBean
	RestaurantFilterService service;
	
	@Autowired
	MockMvc mock;
	
	private final List<RestaurantDTO> results = new ArrayList<RestaurantDTO>();
	private final List<RestaurantDTO> smallResults = new ArrayList<RestaurantDTO>();
	
	@BeforeAll
	public void setUp() {
		LocationDTO location = new LocationDTO("123", "Main", "Sacramento", "CA", 11111);
		List<FoodDTO> foods = new ArrayList<FoodDTO>();
		foods.add(new FoodDTO("Food", 1.00, "It's food"));
		
		RestaurantDTO rest1 = new RestaurantDTO("My Burgers", "Fast Food", location);
		rest1.setFoods(foods);
		RestaurantDTO rest2 = new RestaurantDTO("My Bistro", "French", location);
		rest2.setFoods(foods);
		RestaurantDTO rest3 = new RestaurantDTO("My Pizza", "Pizza", location);
		rest3.setFoods(foods);
		RestaurantDTO rest4 = new RestaurantDTO("My Diner", "Pizza", location);
		rest4.setFoods(foods);
		
		results.add(rest1);
		results.add(rest2);
		results.add(rest3);
		results.add(rest4);
		System.out.println("Stuff added: " + results.toString());
	
		smallResults.add(rest1);
		smallResults.add(rest2);
	}
	
	@Test
	public void filterByZipTest() throws Exception {
		when(service.filterByZip(11111)).thenReturn(results);
		
		mock.perform(get("/restaurants/filter?zip-code=11111")
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(jsonPath("$", hasSize(4)));
	}
	
	@Test
	public void filterByZipEmptyTest() throws Exception {
		when(service.filterByZip(11111)).thenReturn(null);
		
		mock.perform(get("/restaurants/filter?zip-code=11111"))
		.andExpect(MockMvcResultMatchers.status().isNoContent());
	}
	
	@Test
	public void filterByCityTest() throws Exception {
		when(service.filterByCity("Sacramento")).thenReturn(results);
		
		mock.perform(get("/restaurants/filter?city=Sacramento")
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(jsonPath("$", hasSize(4)));
	}
	
	@Test
	public void filterByCityEmptyTest() throws Exception {
		when(service.filterByCity("Sacramento")).thenReturn(null);
		
		mock.perform(get("/restaurants/filter?city=Sacramento"))
		.andExpect(MockMvcResultMatchers.status().isNoContent());
	}
	
	@Test
	public void filterByCuisineTest() throws Exception {
		when(service.filterByCuisine("Any")).thenReturn(results);
		
		mock.perform(get("/restaurants/filter?cuisine=Any")
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(jsonPath("$", hasSize(4)));
	}
	
	@Test
	public void filterByCuisineEmptyTest() throws Exception {
		when(service.filterByCuisine("None")).thenReturn(null);
		
		mock.perform(get("/restaurants/filter?cuisine=None"))
		.andExpect(MockMvcResultMatchers.status().isNoContent());
	}
	
	@Test
	public void filterByMaxPriceTest() throws Exception {
		when(service.lessThanPrice(10.00)).thenReturn(results);
		
		mock.perform(get("/restaurants/filter?price-max=10.00")
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(jsonPath("$", hasSize(4)));
	}
	
	@Test
	public void filterByMaxPriceEmptyTest() throws Exception {
		when(service.lessThanPrice(1.0)).thenReturn(null);
		
		mock.perform(get("/restaurants/filter?price-max=1.0"))
		.andExpect(MockMvcResultMatchers.status().isNoContent());
	}
	
	@Test
	public void filterByMinPriceTest() throws Exception {
		when(service.greaterThanPrice(10.00)).thenReturn(results);
		
		mock.perform(get("/restaurants/filter?price-min=10.00")
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(jsonPath("$", hasSize(4)));
	}
	
	@Test
	public void filterByMinPriceEmptyTest() throws Exception {
		when(service.greaterThanPrice(1.0)).thenReturn(null);
		
		mock.perform(get("/restaurants/filter?price-min=1.0"))
		.andExpect(MockMvcResultMatchers.status().isNoContent());
	}
	
	@Test
	public void filterByPriceRangeTest() throws Exception {
		when(service.inPriceBracket(10.00, 20.00)).thenReturn(results);
		
		mock.perform(get("/restaurants/filter?price-min=10&price-max=20")
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(jsonPath("$", hasSize(4)));
	}
	
	@Test
	public void filterByRangeEmptyTest() throws Exception {
		when(service.inPriceBracket(1.0, 10.0)).thenReturn(null);
		
		mock.perform(get("/restaurants/filter?price-max=10&price-min=1"))
		.andExpect(MockMvcResultMatchers.status().isNoContent());
	}
	
	@Test
	public void filterComboTest() throws Exception {
		when(service.filterByZip(11111)).thenReturn(results);
		when(service.filterByCuisine(results, "Any")).thenReturn(smallResults);
		
		mock.perform(get("/restaurants/filter?zip-code=11111&cuisine=Any")
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(jsonPath("$", hasSize(2)));
		
	}
	
	@Test
	public void filterComboEmptyTest() throws Exception {
		when(service.filterByZip(11111)).thenReturn(results);
		when(service.filterByCuisine(results, "Any")).thenReturn(null);
		
		mock.perform(get("/restaurants/filter?zip-code=11111&cuisine=Any"))
		.andExpect(MockMvcResultMatchers.status().isNoContent());
	}
}
