package com.inthebytes.paymentservice.entity;

import com.stripe.model.Token;

public class PaymentRequest {

	public enum Currency{
		USD,GBP,EUR;
	}
	
	private String description;
	private int amount;
    private Currency currency;
    private String stripeEmail;
    private Token token;
    private String userId;
	    
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	public Currency getCurrency() {
		return currency;
	}
	public void setCurrency(Currency currency) {
		this.currency = currency;
	}
	public String getStripeEmail() {
		return stripeEmail;
	}
	public void setStripeEmail(String stripeEmail) {
		this.stripeEmail = stripeEmail;
	}
	public Token getToken() {
		return token;
	}
	public void setToken(Token stripeToken) {
		this.token = stripeToken;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserId() {
		return this.userId;
	}

}
