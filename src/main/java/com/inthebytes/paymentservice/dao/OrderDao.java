package com.inthebytes.paymentservice.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.inthebytes.paymentservice.entity.Order;

@Repository
public interface OrderDao extends JpaRepository<Order, String> {
	
}
