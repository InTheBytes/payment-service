package com.inthebytes.searchservice.control;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.inthebytes.searchservice.controller.SearchController;
import com.inthebytes.searchservice.dto.FoodDTO;
import com.inthebytes.searchservice.dto.RestaurantDTO;
import com.inthebytes.searchservice.service.SearchService;

@WebMvcTest(SearchController.class)
@AutoConfigureMockMvc
public class SearchControllerTest {
	
	@MockBean
	SearchService service;
	
	@Autowired
	MockMvc mock;
	
	private Page<RestaurantDTO> restaurantPage = Page.empty();
	private Page<FoodDTO> foodPage = Page.empty();
	
	
	@Test
	public void foodSearchTest() throws Exception {
		String[] filter = new String[1];
		when(service.foodSearch("s", "price", filter, false, 1)).thenReturn(foodPage);
		
		mock.perform(get("/search/food")
		        .param("query","s")
		        .param("page","1")
		        .param("sort","high"))
		        .andExpect(status().isOk());
	}
	
	@Test
	public void foodNoParamsTest() throws Exception {
		String[] filter = new String[1];
		when(service.foodSearch("s", "price", filter, true, 0)).thenReturn(foodPage);
		
		mock.perform(get("/search/food")
		        .param("query","s"))
		        .andExpect(status().isOk());
	}
	
	@Test
	public void foodSearchBadParamsTest() throws Exception {
		mock.perform(get("/search/food")
		        .param("query","s")
		        .param("page", "hello"))
		        .andExpect(status().isBadRequest());
	}
	
	@Test
	public void restaurantSearchTest() throws Exception {
		String[] filter = new String[1];
		when(service.restaurantSearch("s", "name", filter, true, 1)).thenReturn(restaurantPage);
		
		mock.perform(get("/search/restaurant")
		        .param("query","s")
		        .param("page", "1"))
		        .andExpect(status().isOk());
	}

	@Test
	public void restaurantSearchNoParamsTest() throws Exception {
		String[] filter = new String[1];
		when(service.restaurantSearch("s", "name", filter, true, 0)).thenReturn(restaurantPage);
		
		mock.perform(get("/search/restaurant")
		        .param("query","s"))
		        .andExpect(status().isOk());
	}
	
	@Test
	public void restaurantSearchBadParamsTest() throws Exception {
		mock.perform(get("/search/restaurant")
		        .param("query","s")
		        .param("page", "hello"))
		        .andExpect(status().isBadRequest());
	}
}
