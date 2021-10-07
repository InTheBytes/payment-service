package com.inthebytes.paymentservice.dto;

import com.stripe.model.Token;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@EqualsAndHashCode
@ToString
public class PaymentRequest {

	public enum Currency{
		USD,GBP,EUR;
	}
	
	private String description;
	private Integer amount;
	private Integer tip;
    private Currency currency;
    private String stripeEmail;
    private Token token;
    private String orderId;
	 
}
