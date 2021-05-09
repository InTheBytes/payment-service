package com.inthebytes.searchservice.mapper;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.inthebytes.searchservice.dto.FoodDTO;
import com.inthebytes.searchservice.dto.LocationDTO;
import com.inthebytes.searchservice.dto.RestaurantDTO;
import com.inthebytes.searchservice.entity.Food;
import com.inthebytes.searchservice.entity.Location;
import com.inthebytes.searchservice.entity.Restaurant;

import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RestaurantMapperTest {
	
	@Autowired
	RestaurantMapper mapper;

	@Test
	public void restaurantEntityToDtoTest() {
		Restaurant tester = makeRestaurantEntity();
		RestaurantDTO result = makeRestaurantDTO();
		
		assertEquals(mapper.convert(tester).toString(), result.toString());
	}
	
	@Test
	public void locationEntityToDtoTest() {
		Location tester = makeRestaurantEntity().getLocation();
		LocationDTO result = makeRestaurantDTO().getLocation();
		
		assertEquals(mapper.convert(tester).toString(), result.toString());
	}
	
	@Test
	public void foodEntityToDtoTest() {
		List<Food> testers = makeRestaurantEntity().getFoods();
		List<FoodDTO> results = makeRestaurantDTO().getFoods();
		
		for (int i = 0; i < testers.size(); i++) {
			assertEquals(mapper.convert(testers.get(i)).toString(), results.get(i).toString());
		}
	}
	
	private RestaurantDTO makeRestaurantDTO() {
		LocationDTO location = new LocationDTO("Main St.", "123", "Sacramento", "California", 11111);
		RestaurantDTO test = new RestaurantDTO("Lexi's Burgers", "Fast Food", location);
		
		FoodDTO food = new FoodDTO("Hamburger", 4.99, "A burger");
		List<FoodDTO> foods = new ArrayList<FoodDTO>();
		foods.add(food);
		test.setFoods(foods);

		return test;
	}
	
	private Restaurant makeRestaurantEntity() {
		Restaurant test = new Restaurant();
		test.setName("Lexi's Burgers");

		Location location = new Location();
		location.setStreet("Main St.");
		location.setUnit("123");
		location.setCity("Sacramento");
		location.setState("California");
		location.setZipCode(11111);
		test.setLocation(location);

		test.setCuisine("Fast Food");
		
		Food food = new Food();
		food.setName("Hamburger");
		food.setPrice(4.99);
		food.setDescription("A burger");
		List<Food> foods = new ArrayList<Food>();
		foods.add(food);
		test.setFoods(foods);

		return test;
	}
}
