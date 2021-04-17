package com.inthebytes.searchservice.filter;

import javax.transaction.Transactional;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.inthebytes.searchservice.repository.RestaurantRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class RestaurantSpecificationTest {

	@Autowired
	RestaurantRepository repo;
	
	@Before
	public void init() {
		
	}
	
	@Test
	void givenCuisineFilterTest() {
		
	}
	
	@Test
	void greaterThanGivenPriceTest() {
		
	}
	
	@Test
	void lessThanGivenPriceTest() {
		
	}
}
