package com.inthebytes.searchservice.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.Persistence;
import javax.transaction.Transactional;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.event.annotation.BeforeTestExecution;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;

import com.inthebytes.searchservice.dao.RestaurantDao;
import com.inthebytes.searchservice.dto.FoodDTO;
import com.inthebytes.searchservice.dto.LocationDTO;
import com.inthebytes.searchservice.dto.RestaurantDTO;
import com.inthebytes.searchservice.entity.Food;
import com.inthebytes.searchservice.entity.Location;
import com.inthebytes.searchservice.entity.Restaurant;
import com.inthebytes.searchservice.mapper.RestaurantMapper;

@RunWith(SpringRunner.class)
@TestInstance(Lifecycle.PER_CLASS)
public class RestaurantFilterServiceTest {


	@Mock
	RestaurantDao repo;
	
	@Mock
	RestaurantMapper mapper;
	
	@InjectMocks
	RestaurantFilterService service;

	private final Restaurant cheapFast = fastFood();
	private final Restaurant cheapDiner = diner();
	private final Restaurant expensiveDiner = expensive();
	
	private final RestaurantDTO cheapFastDTO = fastFoodDTO();
	private final RestaurantDTO cheapDinerDTO = dinerDTO();
	private final RestaurantDTO expensiveDinerDTO = expensiveDTO();

	@BeforeAll
	public void onSetUp() {
		
		MockitoAnnotations.initMocks(this);
		
		cheapFast.representativePrice();
		cheapDiner.representativePrice();
		expensiveDiner.representativePrice();
		
		when(mapper.convert(cheapFast)).thenReturn(cheapFastDTO);
		when(mapper.convert(cheapDiner)).thenReturn(cheapDinerDTO);
		when(mapper.convert(expensiveDiner)).thenReturn(expensiveDinerDTO);

		List<Restaurant> results = new ArrayList<Restaurant>();
		results.add(cheapDiner);
		results.add(cheapFast);
		results.add(expensiveDiner);
		when(repo.findAll()).thenReturn(results);
	}
	
	@Test
	public void filterByCuisineTest() {
		List<RestaurantDTO> diners = service.applyFilters(0, "_", "Diner", 0.0, 0.0);
		assertThat(cheapDinerDTO).isIn(diners);
		assertThat(expensiveDinerDTO).isIn(diners);
		assertThat(cheapFastDTO).isNotIn(diners);
	}
	
	@Test
	public void filterResultsByCuisineTest() {
		List<RestaurantDTO> results = repo.findAll()
				.stream()
				.map((x) -> mapper.convert(x))
				.collect(Collectors.toList());
		List<RestaurantDTO> diners = service.layerFilters(results, 0, "_", "Diner", 0.0, 0.0);
		assertThat(cheapDinerDTO).isIn(diners);
		assertThat(expensiveDinerDTO).isIn(diners);
		assertThat(cheapFastDTO).isNotIn(diners);
	}
	
	@Test
	public void filterByCityTest() {
		List<RestaurantDTO> sac = service.applyFilters(0, "Sacramento", "_", 0.0, 0.0);
		assertThat(cheapFastDTO).isIn(sac);
		assertThat(cheapDinerDTO).isIn(sac);
		assertThat(expensiveDinerDTO).isNotIn(sac);
	}
	
	@Test
	public void filterResultsByCityTest() {
		List<RestaurantDTO> results = repo.findAll()
				.stream()
				.map((x) -> mapper.convert(x))
				.collect(Collectors.toList());
		List<RestaurantDTO> sac = service.layerFilters(results, 0, "Sacramento", "_", 0.0, 0.0);
		assertThat(cheapFastDTO).isIn(sac);
		assertThat(cheapDinerDTO).isIn(sac);
		assertThat(expensiveDinerDTO).isNotIn(sac);
	}
	
	@Test
	public void filterByZipTest() {
		List<RestaurantDTO> sanFran = service.applyFilters(22222, "_", "_", 0.0, 0.0);
		assertThat(expensiveDinerDTO).isIn(sanFran);
		assertThat(cheapFastDTO).isNotIn(sanFran);
		assertThat(cheapDinerDTO).isNotIn(sanFran);
	}
	
	@Test
	public void filterResultsByZipTest() {
		List<RestaurantDTO> results = repo.findAll()
				.stream()
				.map((x) -> mapper.convert(x))
				.collect(Collectors.toList());
		List<RestaurantDTO> sanFran = service.layerFilters(results, 22222, "_", "_", 0.0, 0.0);
		assertThat(expensiveDinerDTO).isIn(sanFran);
		assertThat(cheapFastDTO).isNotIn(sanFran);
		assertThat(cheapDinerDTO).isNotIn(sanFran);
	}
	
	@Test
	public void lessThanPriceTest() {
		List<RestaurantDTO> cheaps = service.applyFilters(0, "_", "_", 5.0, 0.0);
		assertThat(cheapFastDTO).isIn(cheaps);
		assertThat(cheapDinerDTO).isNotIn(cheaps);
		assertThat(expensiveDinerDTO).isNotIn(cheaps);
	}
	
	@Test
	public void lessThanPriceResultsTest() {
		List<RestaurantDTO> results = repo.findAll()
				.stream()
				.map((x) -> mapper.convert(x))
				.collect(Collectors.toList());
		List<RestaurantDTO> cheaps = service.layerFilters(results, 0, "_", "_", 5.0, 0.0);
		assertThat(cheapFastDTO).isIn(cheaps);
		assertThat(cheapDinerDTO).isNotIn(cheaps);
		assertThat(expensiveDinerDTO).isNotIn(cheaps);
	}
	
	@Test
	public void greaterThanPriceTest() {
		List<RestaurantDTO> spendy = service.applyFilters(0, "_", "_", 0.0, 10.0);
		assertThat(expensiveDinerDTO).isIn(spendy);
		assertThat(cheapDinerDTO).isNotIn(spendy);
		assertThat(cheapFastDTO).isNotIn(spendy);
	}
	
	@Test
	public void greaterThanPriceResultsTest() {
		List<RestaurantDTO> results = repo.findAll()
				.stream()
				.map((x) -> mapper.convert(x))
				.collect(Collectors.toList());
		List<RestaurantDTO> spendy = service.layerFilters(results, 0, "_", "_", 0.0, 10.0);
		assertThat(expensiveDinerDTO).isIn(spendy);
		assertThat(cheapDinerDTO).isNotIn(spendy);
		assertThat(cheapFastDTO).isNotIn(spendy);
	}
	
	@Test
	public void inPriceBracketTest() {
		List<RestaurantDTO> range = service.applyFilters(0, "_", "_", 10.0, 5.0);
		assertThat(cheapDinerDTO).isIn(range);
		assertThat(cheapFastDTO).isNotIn(range);
		assertThat(expensiveDinerDTO).isNotIn(range);
	}
	
	@Test
	public void inPriceBracketResultsTest() {
		List<RestaurantDTO> results = repo.findAll()
				.stream()
				.map((x) -> mapper.convert(x))
				.collect(Collectors.toList());
		List<RestaurantDTO> range = service.layerFilters(results, 0, "_", "_", 10.0, 5.0);
		assertThat(cheapDinerDTO).isIn(range);
		assertThat(cheapFastDTO).isNotIn(range);
		assertThat(expensiveDinerDTO).isNotIn(range);
	}
	
	@Test
	public void filterComboTest() {
		List<RestaurantDTO> range = service.applyFilters(11111, "_", "Diner", 0.0, 0.0);
		assertThat(cheapDinerDTO).isIn(range);
		assertThat(cheapFastDTO).isNotIn(range);
		assertThat(expensiveDinerDTO).isNotIn(range);
	}
	
	@Test
	public void filterComboResultsTest() {
		List<RestaurantDTO> results = repo.findAll()
				.stream()
				.map((x) -> mapper.convert(x))
				.collect(Collectors.toList());
		List<RestaurantDTO> range = service.layerFilters(results, 11111, "_", "Diner", 0.0, 0.0);
		assertThat(cheapDinerDTO).isIn(range);
		assertThat(cheapFastDTO).isNotIn(range);
		assertThat(expensiveDinerDTO).isNotIn(range);
	}

	private Restaurant fastFood() {
		Restaurant cheapFast = new Restaurant();
		cheapFast.setRestaurantId(1L);
		cheapFast.setName("Burgers");
		cheapFast.setCuisine("Fast Food");
		cheapFast.setLocation(makeLocation());
		List<Food> cheapFastFood = new ArrayList<Food>();
		cheapFastFood.add(makeFood("Burger", 3.59));
		cheapFastFood.add(makeFood("Fries", 1.99));
		cheapFastFood.add(makeFood("Onion Rings", 1.99));
		cheapFastFood.add(makeFood("Drink", 1.00));
		cheapFast.setFoods(cheapFastFood);
		return cheapFast;
	}

	private Restaurant diner() {
		Restaurant cheapDiner = new Restaurant();
		cheapDiner.setRestaurantId(2L);
		cheapDiner.setName("My Diner");
		cheapDiner.setCuisine("Diner");
		cheapDiner.setLocation(makeLocation());
		List<Food> cheapDinerFood = new ArrayList<Food>();
		cheapDinerFood.add(makeFood("Grand Slam", 8.20));
		cheapDinerFood.add(makeFood("Scrambled Eggs", 4.00));
		cheapDinerFood.add(makeFood("Short Stack", 6.00));
		cheapDinerFood.add(makeFood("Soup", 6.00));
		cheapDinerFood.add(makeFood("Juice", 6.00));
		cheapDinerFood.add(makeFood("Bacon Supreme", 6.00));
		cheapDinerFood.add(makeFood("Muffin", 5.99));
		cheapDinerFood.add(makeFood("Coffee", 3.00));
		cheapDiner.setFoods(cheapDinerFood);
		return cheapDiner;
	}

	private Restaurant expensive() {
		Restaurant expensiveDiner = new Restaurant();
		expensiveDiner.setRestaurantId(3L);
		expensiveDiner.setName("Someone else's Diner");
		expensiveDiner.setCuisine("Diner");
		expensiveDiner.setLocation(makeLocation());
		expensiveDiner.getLocation().setCity("San Francisco");
		expensiveDiner.getLocation().setZipCode(22222);
		List<Food> expensiveDinerFood = new ArrayList<Food>();
		expensiveDinerFood.add(makeFood("Royal Slam", 18.20));
		expensiveDinerFood.add(makeFood("Creme Brule", 21.05));
		expensiveDinerFood.add(makeFood("Wine", 23.00));
		expensiveDiner.setFoods(expensiveDinerFood);
		return expensiveDiner;
	}

	private Location makeLocation() {
		Location location = new Location();
		location.setUnit("123");
		location.setStreet("Main St.");
		location.setCity("Sacramento");
		location.setState("CA");
		location.setZipCode(11111);
		return location;
	}

	private Food makeFood(String name, Double price) {
		Food food = new Food();
		food.setName(name);
		food.setPrice(price);
		food.setDescription("Placeholder food");
		return food;
	}
	
	private RestaurantDTO fastFoodDTO() {
		RestaurantDTO cheapFast = new RestaurantDTO("Burgers", "Fast Food", makeLocationDTO());
		cheapFast.setRestaurantId(1L);
		List<FoodDTO> cheapFastFood = new ArrayList<FoodDTO>();
		cheapFastFood.add(makeFoodDTO("Burger", 3.59));
		cheapFastFood.add(makeFoodDTO("Fries", 1.99));
		cheapFastFood.add(makeFoodDTO("Onion Rings", 1.99));
		cheapFastFood.add(makeFoodDTO("Drink", 1.00));
		cheapFast.setFoods(cheapFastFood);
		return cheapFast;
	}

	private RestaurantDTO dinerDTO() {
		RestaurantDTO cheapDiner = new RestaurantDTO("My Diner", "Diner", makeLocationDTO());
		cheapDiner.setRestaurantId(2L);
		List<FoodDTO> cheapDinerFood = new ArrayList<FoodDTO>();
		cheapDinerFood.add(makeFoodDTO("Grand Slam", 8.20));
		cheapDinerFood.add(makeFoodDTO("Scrambled Eggs", 4.00));
		cheapDinerFood.add(makeFoodDTO("Short Stack", 6.00));
		cheapDinerFood.add(makeFoodDTO("Soup", 6.00));
		cheapDinerFood.add(makeFoodDTO("Juice", 6.00));
		cheapDinerFood.add(makeFoodDTO("Bacon Supreme", 6.00));
		cheapDinerFood.add(makeFoodDTO("Muffin", 5.99));
		cheapDinerFood.add(makeFoodDTO("Coffee", 3.00));
		cheapDiner.setFoods(cheapDinerFood);
		return cheapDiner;
	}

	private RestaurantDTO expensiveDTO() {
		RestaurantDTO expensiveDiner = new RestaurantDTO("Someone else's Diner", "Diner", makeLocationDTO());
		expensiveDiner.setRestaurantId(3L);
		expensiveDiner.getLocation().setCity("San Francisco");
		expensiveDiner.getLocation().setZipCode(22222);
		List<FoodDTO> expensiveDinerFood = new ArrayList<FoodDTO>();
		expensiveDinerFood.add(makeFoodDTO("Royal Slam", 18.20));
		expensiveDinerFood.add(makeFoodDTO("Creme Brule", 21.05));
		expensiveDinerFood.add(makeFoodDTO("Wine", 23.00));
		expensiveDiner.setFoods(expensiveDinerFood);
		return expensiveDiner;
	}

	private LocationDTO makeLocationDTO() {
		LocationDTO location = new LocationDTO("123", "Main St.", "Sacramento", "CA", 11111);
		return location;
	}

	private FoodDTO makeFoodDTO(String name, Double price) {
		FoodDTO food = new FoodDTO(name, price, "Placeholder food");
		return food;
	}
}
