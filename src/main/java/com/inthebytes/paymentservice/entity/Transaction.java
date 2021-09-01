package com.inthebytes.paymentservice.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;


@Entity
@Table(name = "transaction")
public class Transaction implements Serializable {

	private static final long serialVersionUID = -8945065777365899540L;

	@Id
	@GeneratedValue(generator = "UUID")
	@GenericGenerator(
	    name = "UUID",
	    strategy = "org.hibernate.id.UUIDGenerator"
	)
	@Column(name = "transaction_id")
	private String transactionId;
	
	@Column(name = "stripe_id")
	private String stripeId;
	
	@OneToOne
	@JoinColumn(name = "order_id")
	private Order order;
	
	@Column(name = "subtotal")
	private BigDecimal subtotal;
	
	@Column(name = "tax")
	private BigDecimal tax;
	
	@Column(name = "fee")
	private BigDecimal fee;
	
	@Column(name = "discount")
	private BigDecimal discount;
	
	@Column(name = "tip")
	private BigDecimal tip;
	
	@Column(name = "total")
	private BigDecimal total;
	
	@Column(name = "payment_time")
	private Timestamp paymentTime;

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public String getStripeId() {
		return stripeId;
	}

	public void setStripeId(String stripeId) {
		this.stripeId = stripeId;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public BigDecimal getSubtotal() {
		return subtotal;
	}

	public void setSubtotal(BigDecimal subtotal) {
		this.subtotal = subtotal;
	}

	public BigDecimal getTax() {
		return tax;
	}

	public void setTax(BigDecimal tax) {
		this.tax = tax;
	}

	public BigDecimal getFee() {
		return fee;
	}

	public void setFee(BigDecimal fee) {
		this.fee = fee;
	}

	public BigDecimal getDiscount() {
		return discount;
	}

	public void setDiscount(BigDecimal discount) {
		this.discount = discount;
	}

	public BigDecimal getTip() {
		return tip;
	}

	public void setTip(BigDecimal tip) {
		this.tip = tip;
	}

	public BigDecimal getTotal() {
		return total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}

	public Timestamp getPaymentTime() {
		return paymentTime;
	}

	public void setPaymentTime(Timestamp paymentTime) {
		this.paymentTime = paymentTime;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((discount == null) ? 0 : discount.hashCode());
		result = prime * result + ((fee == null) ? 0 : fee.hashCode());
		result = prime * result + ((order == null) ? 0 : order.hashCode());
		result = prime * result + ((paymentTime == null) ? 0 : paymentTime.hashCode());
		result = prime * result + ((stripeId == null) ? 0 : stripeId.hashCode());
		result = prime * result + ((subtotal == null) ? 0 : subtotal.hashCode());
		result = prime * result + ((tax == null) ? 0 : tax.hashCode());
		result = prime * result + ((tip == null) ? 0 : tip.hashCode());
		result = prime * result + ((total == null) ? 0 : total.hashCode());
		result = prime * result + ((transactionId == null) ? 0 : transactionId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Transaction other = (Transaction) obj;
		if (discount == null) {
			if (other.discount != null)
				return false;
		} else if (!discount.equals(other.discount))
			return false;
		if (fee == null) {
			if (other.fee != null)
				return false;
		} else if (!fee.equals(other.fee))
			return false;
		if (order == null) {
			if (other.order != null)
				return false;
		} else if (!order.equals(other.order))
			return false;
		if (paymentTime == null) {
			if (other.paymentTime != null)
				return false;
		} else if (!paymentTime.equals(other.paymentTime))
			return false;
		if (stripeId == null) {
			if (other.stripeId != null)
				return false;
		} else if (!stripeId.equals(other.stripeId))
			return false;
		if (subtotal == null) {
			if (other.subtotal != null)
				return false;
		} else if (!subtotal.equals(other.subtotal))
			return false;
		if (tax == null) {
			if (other.tax != null)
				return false;
		} else if (!tax.equals(other.tax))
			return false;
		if (tip == null) {
			if (other.tip != null)
				return false;
		} else if (!tip.equals(other.tip))
			return false;
		if (total == null) {
			if (other.total != null)
				return false;
		} else if (!total.equals(other.total))
			return false;
		if (transactionId == null) {
			if (other.transactionId != null)
				return false;
		} else if (!transactionId.equals(other.transactionId))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Transaction [transactionId=" + transactionId + ", stripeId=" + stripeId + ", order=" + order
				+ ", subtotal=" + subtotal + ", tax=" + tax + ", fee=" + fee + ", discount=" + discount + ", tip=" + tip
				+ ", total=" + total + ", paymentTime=" + paymentTime + "]";
	}
}
