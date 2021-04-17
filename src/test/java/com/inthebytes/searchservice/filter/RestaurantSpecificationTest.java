package com.inthebytes.searchservice.filter;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Persistence;
import javax.transaction.Transactional;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.event.annotation.BeforeTestExecution;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.inthebytes.searchservice.entity.Food;
import com.inthebytes.searchservice.entity.Location;
import com.inthebytes.searchservice.entity.Restaurant;
import com.inthebytes.searchservice.repository.RestaurantRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@TestInstance(Lifecycle.PER_CLASS)
@DataJpaTest
//@Transactional
@EnableJpaRepositories(basePackages = "com.inthebytes.searchservice.repository")
public class RestaurantSpecificationTest {

	@Autowired
	RestaurantRepository repo;
	
	private final Restaurant cheapFast = fastFood();
	private final Restaurant cheapDiner = diner();
	private final Restaurant expensiveDiner = expensive();
	
	@BeforeAll
	public void onSetUp() {
		
		cheapFast.manuallyLoadPrice();
		cheapDiner.manuallyLoadPrice();
		expensiveDiner.manuallyLoadPrice();
		
		repo.save(cheapFast);
		repo.save(cheapDiner);
		repo.save(expensiveDiner);
	}
	
	@Test
	void givenCuisineFilterTest() {
		RestaurantSpecification dinerSpec = 
				new RestaurantSpecification(new FilterCriteria("cuisine", ":", "Diner"));
		
		List<Restaurant> diners = repo.findAll(dinerSpec);
		assertThat(cheapDiner).isIn(diners);
		assertThat(expensiveDiner).isIn(diners);
		assertThat(cheapFast).isNotIn(diners);
		
		RestaurantSpecification fastSpec = 
				new RestaurantSpecification(new FilterCriteria("cuisine", ":", "Fast Food"));
		List<Restaurant> fast = repo.findAll(fastSpec);
		assertThat(cheapFast).isIn(fast);
		assertThat(cheapDiner).isNotIn(fast);
		assertThat(expensiveDiner).isNotIn(fast);
	}
	
	@Test
	void greaterThanGivenPriceTest() {
		
	}
	
	@Test
	void lessThanGivenPriceTest() {
		
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
}
