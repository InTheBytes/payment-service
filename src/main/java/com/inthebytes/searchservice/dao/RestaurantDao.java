package com.inthebytes.searchservice.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import com.inthebytes.searchservice.entity.Restaurant;

public interface RestaurantDao extends JpaRepository<Restaurant, Long> {

}
