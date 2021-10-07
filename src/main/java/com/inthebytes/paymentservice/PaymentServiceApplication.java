package com.inthebytes.paymentservice;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.inthebytes.stacklunch.StackLunchApplication;
import com.inthebytes.stacklunch.data.order.OrderRepository;
import com.inthebytes.stacklunch.data.transaction.TransactionRepository;

@SpringBootApplication
@EnableJpaRepositories(basePackageClasses = {
		OrderRepository.class, TransactionRepository.class
})
public class PaymentServiceApplication {

	public static void main(String[] args) {
		StackLunchApplication.run(PaymentServiceApplication.class, args);
	}
}
