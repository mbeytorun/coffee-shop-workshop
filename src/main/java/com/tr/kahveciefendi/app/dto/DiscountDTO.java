package com.tr.kahveciefendi.app.dto;

public class DiscountDTO {
	
	private String totalAmount;
	private String paidAmount;
	private DiscountType discountType;
	
	public DiscountDTO(){
		
	}
	
	public DiscountDTO(String totalAmount, String paidAmount, DiscountType discountType) {
		this.totalAmount = totalAmount;
		this.paidAmount = paidAmount;
		this.discountType = discountType;
	}

	public String getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(String totalAmount) {
		this.totalAmount = totalAmount;
	}

	public String getPaidAmount() {
		return paidAmount;
	}

	public void setPaidAmount(String paidAmount) {
		this.paidAmount = paidAmount;
	}

	public DiscountType getDiscountType() {
		return discountType;
	}

	public void setDiscountType(DiscountType discountType) {
		this.discountType = discountType;
	}

}
