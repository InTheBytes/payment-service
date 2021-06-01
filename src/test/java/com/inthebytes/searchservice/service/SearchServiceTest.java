package com.inthebytes.searchservice.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.sql.SQLException;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringRunner;

import com.inthebytes.searchservice.dao.FoodDao;
import com.inthebytes.searchservice.dao.RestaurantDao;
import com.inthebytes.searchservice.dto.FoodDTO;
import com.inthebytes.searchservice.dto.RestaurantDTO;
import com.inthebytes.searchservice.entity.Food;
import com.inthebytes.searchservice.entity.Restaurant;
import com.inthebytes.searchservice.mapper.RestaurantMapper;

@RunWith(SpringRunner.class)
@TestInstance(Lifecycle.PER_CLASS)
public class SearchServiceTest {

	@Mock
	FoodDao foodRepo;
	
	@Mock
	RestaurantDao restaurantRepo;
	
	@Mock
	RestaurantMapper mapper;
	
	@InjectMocks
	SearchService service;
	
	@BeforeAll
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void foodSearchTest() throws SQLException {
		Page<Food> page = Page.empty();
		Page<FoodDTO> result = Page.empty();
		String[] filter = new String[1];
		when(foodRepo.findByNameContaining("", PageRequest.of(1, 10, Sort.by(Sort.Direction.ASC, "low")))).thenReturn(page);
		
		assertThat(service.foodSearch("", "low", filter, true, 1)).isEqualTo(result);
	}
	
	@Test
	public void restaurantSearchTest() throws SQLException {
		Page<Restaurant> page = Page.empty();
		Page<RestaurantDTO> result = Page.empty();
		String[] filter = new String[1];
		when(restaurantRepo.findByNameContaining("", PageRequest.of(1, 10, Sort.by(Sort.Direction.ASC, "low")))).thenReturn(page);
		
		assertThat(service.restaurantSearch("", "low", filter, true, 1)).isEqualTo(result);
	}
}
