package com.inthebytes.searchservice.control;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.annotation.RequestParam;

import com.inthebytes.searchservice.dto.RestaurantDTO;
import com.inthebytes.searchservice.service.RestaurantFilterService;

@WebMvcTest(RestaurantFilterController.class)
@AutoConfigureMockMvc
public class RestaurantFilterControllerTest {

	@MockBean
	RestaurantFilterService service;
	
	@Autowired
	MockMvc mock;
	
	@Test
	public void filterByZipTest() {
		
	}
	
	@Test
	public void filterByCityTest() {
		
	}
	
	@Test
	public void filterByCuisineTest() {
		
	}
	
	@Test
	public void filterByMaxPriceTest() {
		
	}
	
	@Test
	public void filterByMinPriceTest() {
		
	}
	
	@Test
	public void filterByPriceRangeTest() {
		
	}
	
	@Test
	public void filterComboTest() {
		
	}
}
