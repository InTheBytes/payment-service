package com.inthebytes.paymentservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.inthebytes.paymentservice.dto.PaymentRequest;
import com.inthebytes.paymentservice.service.PaymentService;
import com.stripe.exception.StripeException;

@RestController
@RequestMapping("/payment")
public class PaymentController {
    
    @Autowired
    private PaymentService payService;

    @PostMapping("/stripe")
	public ResponseEntity<String> stripeCharge(@RequestBody PaymentRequest request) throws StripeException {
		String chargeId = payService.stripeCharge(request);
		if (chargeId==null) {
			return new ResponseEntity<String>("There was a problem with your payment. Please try again.",HttpStatus.BAD_REQUEST);
		} else {
			return new ResponseEntity<String>(chargeId, HttpStatus.OK);
		}
	}

}