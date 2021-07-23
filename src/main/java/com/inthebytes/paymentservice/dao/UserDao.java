package com.inthebytes.paymentservice.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.inthebytes.paymentservice.entity.User;

@Repository
public interface UserDao extends JpaRepository<User, String> {
	
}
