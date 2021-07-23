package com.inthebytes.paymentservice.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.inthebytes.paymentservice.entity.Payment;

@Repository
public interface PaymentDao extends JpaRepository<Payment, String> {
	
}
