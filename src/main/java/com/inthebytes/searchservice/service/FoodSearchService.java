package com.inthebytes.searchservice.service;

import com.inthebytes.searchservice.dao.FoodDao;
import com.inthebytes.searchservice.entity.Food;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FoodSearchService {

	@Autowired
	FoodDao foodDao;

	public List<Food> viewFoods() {
		return foodDao.findAll();
	}

	public Page<Food> sortFoods(String option, Boolean ascending, Integer pageNumber) {
		switch (option) {
			case "price":
				return foodDao.findAll(PageRequest.of(pageNumber, 10, Sort.by((ascending)? Sort.Direction.ASC : Sort.Direction.DESC, option)));
			case "star":
				return foodDao.findAll(PageRequest.of(pageNumber, 10));
			default:
				return null;
		}
	}

}
