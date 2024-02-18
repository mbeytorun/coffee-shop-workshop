package com.tr.kahveciefendi.app.dto;

import java.sql.Time;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.tr.kahveciefendi.app.dto.serialization.CustomTimeDeserializer;
import com.tr.kahveciefendi.app.dto.serialization.CustomTimeSerializer;
import com.tr.kahveciefendi.app.model.Order;
import com.tr.kahveciefendi.app.model.OrderItem;

public class OrderDTO {
	
	private Long id;
	
	private UserInfoDTO user;
	private List<OrderItem> orderItems;
	   
	@JsonFormat(pattern = "yyyy/MM/dd", timezone = "CET")
	private Date date;
   
	@JsonSerialize(using = CustomTimeSerializer.class)
	@JsonDeserialize(using = CustomTimeDeserializer.class)
	private Time time;
    private String totalAmount;
    private String paidAmount;
    private DiscountType discountType;
    
    public OrderDTO(){
    	
    }
    
    public OrderDTO(Long id, UserInfoDTO user, List<OrderItem> orderItems, Date date, Time time, String totalAmount,
			String paidAmount) {
		this.id = id;
		this.user = user;
		this.orderItems = orderItems;
		this.date = date;
		this.time = time;
		this.totalAmount = totalAmount;
		this.paidAmount = paidAmount;
		this.discountType = DiscountType.None;
	}
    
	public OrderDTO(Long id, UserInfoDTO user, List<OrderItem> orderItems, Date date, Time time, String totalAmount,
			String paidAmount, DiscountType discountType) {
		this.id = id;
		this.user = user;
		this.orderItems = orderItems;
		this.date = date;
		this.time = time;
		this.totalAmount = totalAmount;
		this.paidAmount = paidAmount;
		this.discountType = discountType;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}

	public UserInfoDTO getUser() {
		return user;
	}

	public void setUser(UserInfoDTO user) {
		this.user = user;
	}

	public List<OrderItem> getOrderItems() {
		return orderItems;
	}

	public void setOrderItems(List<OrderItem> orderItems) {
		this.orderItems = orderItems;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Time getTime() {
		return time;
	}

	public void setTime(Time time) {
		this.time = time;
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

	public static OrderDTO mapFromOrderEntity(Order order) {
		UserInfoDTO userInfoDTO = new UserInfoDTO(order.getUser().getUsername(), order.getUser().getId());
		return new OrderDTO(order.getId(), userInfoDTO, order.getOrderItems(), order.getDate(), order.getTime(), 
				order.getTotalAmount().toString(), order.getPaidAmount().toString(), order.getDiscountType());
	}

	public DiscountType getDiscountType() {
		return discountType;
	}

	public void setDiscountType(DiscountType discountType) {
		this.discountType = discountType;
	}

}
