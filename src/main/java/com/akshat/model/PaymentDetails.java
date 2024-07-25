package com.akshat.model;

public class PaymentDetails {

	private String paymentMethod;
	private String status;
	
	private String paymentId;
	
	private String rozorpayPaymentLinkId;
	private String  razorpayPaymentLinkReferenceId;
	private String  rozorpayPaymentLinkStatus;
	private String  rozorpayPaymentId;
	
	public PaymentDetails() {
		
	}
	
	
	
	



	public String getPaymentMethod() {
		return paymentMethod;
	}
	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getPaymentId() {
		return paymentId;
	}
	public void setPaymentId(String paymentId) {
		this.paymentId = paymentId;
	}
	public String getRozorpayPaymentLinkId() {
		return rozorpayPaymentLinkId;
	}
	public void setRozorpayPaymentLinkId(String rozorpayPaymentLinkId) {
		this.rozorpayPaymentLinkId = rozorpayPaymentLinkId;
	}
	public String getRazorpayPaymentLinkReferenceId() {
		return razorpayPaymentLinkReferenceId;
	}
	public void setRazorpayPaymentLinkReferenceId(String razorpayPaymentLinkReferenceId) {
		this.razorpayPaymentLinkReferenceId = razorpayPaymentLinkReferenceId;
	}
	public String getRozorpayPaymentLinkStatus() {
		return rozorpayPaymentLinkStatus;
	}
	public void setRozorpayPaymentLinkStatus(String rozorpayPaymentLinkStatus) {
		this.rozorpayPaymentLinkStatus = rozorpayPaymentLinkStatus;
	}
	public String getRozorpayPaymentId() {
		return rozorpayPaymentId;
	}
	public void setRozorpayPaymentId(String rozorpayPaymentId) {
		this.rozorpayPaymentId = rozorpayPaymentId;
	}
	
	

}
