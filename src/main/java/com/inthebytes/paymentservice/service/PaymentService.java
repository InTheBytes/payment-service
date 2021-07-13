package com.inthebytes.paymentservice.service;

import java.time.YearMonth;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.inthebytes.paymentservice.dao.PaymentDao;
import com.inthebytes.paymentservice.dao.UserDao;
import com.inthebytes.paymentservice.entity.Payment;
import com.inthebytes.paymentservice.entity.PaymentRequest;
import com.inthebytes.paymentservice.entity.User;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;

@Service
public class PaymentService {
	
	@Autowired
	private PaymentDao payRepo;
	
	@Autowired
	private UserDao userRepo;
	
	@Value("${STRIPE_SECRET_KEY}")
    private String API_SECRET_KEY;

    @PostConstruct
    public void init() {
        Stripe.apiKey = API_SECRET_KEY;
    }
	public String stripeCharge(PaymentRequest request) throws StripeException {
		Map<String, Object> chargeParams = new HashMap<>();
	    chargeParams.put("amount", request.getAmount());
	    chargeParams.put("currency", request.getCurrency());
	    chargeParams.put("source", request.getToken().getId());
		Charge charge = Charge.create(chargeParams);
		savePayment(request, "Stripe");
		return charge.getId();
	}
	
	public String alineCharge(PaymentRequest request) {
		return "Not implemented";
	}
	
	//TODO: Discuss consolidation of transaction and payment tables; much of this information is either unnecessary or too secure, and we should be abstracting it to Stripe and Aline.
	
	private void savePayment(PaymentRequest request, String method) {
		Payment payment = new Payment();
		User user = userRepo.getOne(request.getUserId());
		payment.setUser(user);
		payment.setMethod(method);
		payment.setNickname("Nickname");
		payment.setExpiration(Date.from(YearMonth.of(2029, 12).atDay(1).atStartOfDay(ZoneId.systemDefault()).toInstant()));
		payment.setAccountNum("Account Number");
		payment.setCode("Code");
		payment.setHolder("Test User");
		payRepo.save(payment);
	}
}