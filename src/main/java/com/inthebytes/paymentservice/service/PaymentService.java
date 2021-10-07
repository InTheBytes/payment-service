package com.inthebytes.paymentservice.service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.inthebytes.paymentservice.dto.PaymentRequest;
import com.inthebytes.stacklunch.data.order.Order;
import com.inthebytes.stacklunch.data.order.OrderRepository;
import com.inthebytes.stacklunch.data.transaction.Transaction;
import com.inthebytes.stacklunch.data.transaction.TransactionRepository;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.BalanceTransaction;
import com.stripe.model.Charge;

@Service
public class PaymentService {
	
	@Autowired
	private TransactionRepository transRepo;
	
	@Autowired
	private OrderRepository orderRepo;
	
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
	    List<String> expandParams = new ArrayList<String>();
	    expandParams.add("balance_transaction");
		chargeParams.put("expand", expandParams);
		Charge charge = Charge.create(chargeParams);
		return saveTransaction(request, charge).getStripeId();
	}
	
	public String alineCharge(PaymentRequest request) {
		return "Not implemented";
	}
	
	@Transactional
	private Transaction saveTransaction(PaymentRequest request, Charge charge) {
		Transaction transaction = new Transaction();
		Order order = orderRepo.getOne(request.getOrderId());
		order.setStatus(1);
		transaction.setOrder(orderRepo.save(order));
		transaction.setStripeId(charge.getId());
		transaction.setPaymentTime(Timestamp.from(Instant.now()));
		transaction.setSubtotal(BigDecimal.valueOf((double) request.getAmount()));
		BalanceTransaction bt = charge.getBalanceTransactionObject();
		transaction.setFee(BigDecimal.valueOf(bt.getFee()));
		transaction.setTotal(BigDecimal.valueOf(bt.getAmount()));
		transaction.setTip(BigDecimal.valueOf((double) request.getTip()));
		
		transaction.setDiscount(BigDecimal.ZERO);
		transaction.setTax(BigDecimal.ZERO);
		
		return transRepo.save(transaction);
	}
}