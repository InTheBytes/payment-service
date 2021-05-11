package com.inthebytes.searchservice.control;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.util.ArrayList;
import java.util.List;

import com.inthebytes.searchservice.controller.RestaurantFilterController;

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
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.inthebytes.searchservice.dto.FoodDTO;
import com.inthebytes.searchservice.dto.LocationDTO;
import com.inthebytes.searchservice.dto.RestaurantDTO;
import com.inthebytes.searchservice.service.RestaurantFilterService;

@WebMvcTest(RestaurantFilterController.class)
@AutoConfigureMockMvc
@TestInstance(Lifecycle.PER_CLASS)
public class RestaurantFilterControllerTest {
	
	private String endpoint = "/filter/restaurants";

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
		when(service.applyFilters(11111, "_", "_", 0.0, 0.0)).thenReturn(results);

		mock.perform(get(endpoint + "?zip-code=11111")
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(jsonPath("$", hasSize(4)));
	}

	@Test
	public void filterByZipEmptyTest() throws Exception {
		when(service.applyFilters(11111, "_", "_", 0.0, 0.0)).thenReturn(null);

		mock.perform(get(endpoint + "?zip-code=11111"))
		.andExpect(MockMvcResultMatchers.status().isNoContent());
	}

	@Test
	public void filterResultByZipTest() throws Exception {
		when(service.layerFilters(results, 11111, "_", "_", 0.0, 0.0)).thenReturn(smallResults);

		mock.perform(post(endpoint + "?zip-code=11111")
				.contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(results)))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(jsonPath("$", hasSize(2)));
	}

	@Test
	public void filterResultsByZipEmptyTest() throws Exception {
		when(service.layerFilters(results, 11111, "_", "_", 0.0, 0.0)).thenReturn(null);

		mock.perform(post(endpoint + "?zip-code=11111")
				.contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(results)))
		.andExpect(MockMvcResultMatchers.status().isNoContent());
	}

	@Test
	public void filterByCityTest() throws Exception {
		when(service.applyFilters(0, "Sacramento", "_", 0.0, 0.0)).thenReturn(results);

		mock.perform(get(endpoint + "?city=Sacramento")
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(jsonPath("$", hasSize(4)));
	}

	@Test
	public void filterByCityEmptyTest() throws Exception {
		when(service.applyFilters(0, "Sacramento", "_", 0.0, 0.0)).thenReturn(null);

		mock.perform(get(endpoint + "?city=Sacramento"))
		.andExpect(MockMvcResultMatchers.status().isNoContent());
	}
	
	@Test
	public void filterResultsByCityTest() throws Exception {
		when(service.layerFilters(results, 0, "Sacramento", "_", 0.0, 0.0)).thenReturn(smallResults);

		mock.perform(post(endpoint + "?city=Sacramento")
				.contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(results)))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(jsonPath("$", hasSize(2)));
	}

	@Test
	public void filterResultsByCityEmptyTest() throws Exception {
		when(service.layerFilters(results, 0, "Sacramento", "_", 0.0, 0.0)).thenReturn(null);

		mock.perform(post(endpoint + "?city=Sacramento")
				.contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(results)))
		.andExpect(MockMvcResultMatchers.status().isNoContent());
	}

	@Test
	public void filterByCuisineTest() throws Exception {
		when(service.applyFilters(0, "_", "Any", 0.0, 0.0)).thenReturn(results);

		mock.perform(get(endpoint + "?cuisine=Any")
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(jsonPath("$", hasSize(4)));
	}

	@Test
	public void filterByCuisineEmptyTest() throws Exception {
		when(service.applyFilters(0, "_", "Any", 0.0, 0.0)).thenReturn(null);

		mock.perform(get(endpoint + "?cuisine=Any"))
		.andExpect(MockMvcResultMatchers.status().isNoContent());
	}
	
	
	@Test
	public void filterResultsByCuisineTest() throws Exception {
		when(service.layerFilters(results, 0, "_", "Any", 0.0, 0.0)).thenReturn(smallResults);

		mock.perform(post(endpoint + "?cuisine=Any")
				.contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(results)))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(jsonPath("$", hasSize(2)));
	}

	@Test
	public void filterResultsByCuisineEmptyTest() throws Exception {
		when(service.layerFilters(results, 0, "_", "Any", 0.0, 0.0)).thenReturn(null);

		mock.perform(post(endpoint + "?cuisine=None")
				.contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(results)))
		.andExpect(MockMvcResultMatchers.status().isNoContent());
	}

	@Test
	public void filterByMaxPriceTest() throws Exception {
		when(service.applyFilters(0, "_", "_", 10.00, 0.0)).thenReturn(results);

		mock.perform(get(endpoint + "?price-max=10.00")
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(jsonPath("$", hasSize(4)));
	}

	@Test
	public void filterByMaxPriceEmptyTest() throws Exception {
		when(service.applyFilters(0, "_", "_", 1.0, 0.0)).thenReturn(null);

		mock.perform(get(endpoint + "?price-max=1.0"))
		.andExpect(MockMvcResultMatchers.status().isNoContent());
	}
	
	@Test
	public void filterResultByMaxPriceTest() throws Exception {
		when(service.layerFilters(results, 0, "_", "_", 10.00, 0.0)).thenReturn(smallResults);

		mock.perform(post(endpoint + "?price-max=10.00")
				.contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(results)))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(jsonPath("$", hasSize(2)));
	}

	@Test
	public void filterResultsByMaxPriceEmptyTest() throws Exception {
		when(service.layerFilters(results, 0, "_", "_", 10.00, 0.0)).thenReturn(null);

		mock.perform(post(endpoint + "?price-max=1.0")
				.contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(results)))
		.andExpect(MockMvcResultMatchers.status().isNoContent());
	}

	@Test
	public void filterByMinPriceTest() throws Exception {
		when(service.applyFilters(0, "_", "_", 0.0, 10.00)).thenReturn(results);

		mock.perform(get(endpoint + "?price-min=10.00")
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(jsonPath("$", hasSize(4)));
	}

	@Test
	public void filterByMinPriceEmptyTest() throws Exception {
		when(service.applyFilters(0, "_", "_", 0.0, 1.00)).thenReturn(null);

		mock.perform(get(endpoint + "?price-min=1.0"))
		.andExpect(MockMvcResultMatchers.status().isNoContent());
	}
	
	@Test
	public void filterResultByMinPriceTest() throws Exception {
		when(service.layerFilters(results, 0, "_", "_", 0.0, 10.00)).thenReturn(smallResults);

		mock.perform(post(endpoint + "?price-min=10.00")
				.contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(results)))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(jsonPath("$", hasSize(2)));
	}

	@Test
	public void filterResultByMinPriceEmptyTest() throws Exception {
		when(service.layerFilters(results, 0, "_", "_", 0.0, 1.00)).thenReturn(null);

		mock.perform(post(endpoint + "?price-min=1.0")
				.contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(results)))
		.andExpect(MockMvcResultMatchers.status().isNoContent());
	}

	@Test
	public void filterByPriceRangeTest() throws Exception {
		when(service.applyFilters(0, "_", "_", 20.00, 10.00)).thenReturn(results);

		mock.perform(get(endpoint + "?price-min=10.0&price-max=20.0")
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(jsonPath("$", hasSize(4)));
	}

	@Test
	public void filterByRangeEmptyTest() throws Exception {
		when(service.applyFilters(0, "_", "_", 10.0, 1.0)).thenReturn(null);

		mock.perform(get(endpoint + "?price-max=10.0&price-min=1.0"))
		.andExpect(MockMvcResultMatchers.status().isNoContent());
	}
	
	@Test
	public void filterResultByPriceRangeTest() throws Exception {
		when(service.layerFilters(results, 0, "_", "_", 20.00, 10.00)).thenReturn(smallResults);

		mock.perform(post(endpoint + "?price-min=10.0&price-max=20.0")
				.contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(results)))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(jsonPath("$", hasSize(2)));
	}

	@Test
	public void filterResultByRangeEmptyTest() throws Exception {
		when(service.layerFilters(results, 0, "_", "_", 1.0, 10.0)).thenReturn(null);

		mock.perform(post(endpoint + "?price-max=10&price-min=1")
				.contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(results)))
		.andExpect(MockMvcResultMatchers.status().isNoContent());
	}

	@Test
	public void filterComboTest() throws Exception {
		when(service.applyFilters(11111, "_", "Any", 0.0, 0.0)).thenReturn(smallResults);

		mock.perform(get(endpoint + "?zip-code=11111&cuisine=Any")
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(jsonPath("$", hasSize(2)));

	}

	@Test
	public void filterComboEmptyTest() throws Exception {
		when(service.applyFilters(11111, "_", "Any", 0.0, 0.0)).thenReturn(null);

		mock.perform(get(endpoint + "?zip-code=11111&cuisine=Any"))
		.andExpect(MockMvcResultMatchers.status().isNoContent());
	}
	
	@Test
	public void filterComboPostTest() throws Exception {
		when(service.layerFilters(results, 11111, "_", "Any", 0.0, 0.0)).thenReturn(smallResults);

		mock.perform(post(endpoint + "?zip-code=11111&cuisine=Any")
				.contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(results)))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(jsonPath("$", hasSize(2)));

	}

	@Test
	public void filterComboEmptyPostTest() throws Exception {
		when(service.layerFilters(results, 11111, "_", "Any", 0.0, 0.0)).thenReturn(null);

		mock.perform(post(endpoint + "?zip-code=11111&cuisine=Any")
				.contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(results)))
		.andExpect(MockMvcResultMatchers.status().isNoContent());
	}
}
