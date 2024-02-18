package com.tr.kahveciefendi.app.model;

import java.math.BigDecimal;
import java.sql.Time;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.tr.kahveciefendi.app.dto.DiscountType;

/**
*
* Order JPA entity
*
*/
@Entity
@Table(name = "ORDERS")
@NamedQueries({
	@NamedQuery(
        name = Order.FIND_BY_USERID,
        query = "select o from Order o where o.user.id = :userid"),
	@NamedQuery(
	        name = Order.GET_USER_TOTALS,
	        query = "select o.user.id, u.username, sum(o.totalAmount), sum(o.paidAmount), count(*) from Order o join o.user u"
	        		+ " group by o.user.id, u.username"),
	@NamedQuery(
	        name = Order.GET_BEVERAGE_TOTALS,
	        query = "select o.beverage.id, o.beverage.name, count(*), sum(o.beverage.price) from OrderItem o"
	        		+ " group by o.beverage.id, o.beverage.name")
})
public class Order extends AbstractEntity {
	
	public static final String FIND_BY_USERID = "order.findByUserId";
	public static final String GET_USER_TOTALS = "order.getUserTotals";
	public static final String GET_BEVERAGE_TOTALS = "order.getBeverageTotals";
	
	@ManyToOne
    private User user;
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinTable
    private List<OrderItem> orderItems;
	
	private Date date;
    private Time time;
    private BigDecimal totalAmount;
    private BigDecimal paidAmount;
    private DiscountType discountType;
    
    public Order(){
    	
    }
    
	public Order(User user, List<OrderItem> orderItems, Date date, Time time, BigDecimal totalAmount,
			BigDecimal paidAmount) {
		this.user = user;
		this.orderItems = orderItems;
		this.date = date;
		this.time = time;
		this.totalAmount = totalAmount;
		this.paidAmount = paidAmount;
		this.discountType = DiscountType.None;
	}
	
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public List<OrderItem> getOrderItems() {
		return orderItems;
	}
	public void setItems(List<OrderItem> orderItems) {
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
	public BigDecimal getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}
	public BigDecimal getPaidAmount() {
		return paidAmount;
	}
	public void setPaidAmount(BigDecimal paidAmount) {
		this.paidAmount = paidAmount;
	}
	public DiscountType getDiscountType() {
		return discountType;
	}
	public void setDiscountType(DiscountType discountType) {
		this.discountType = discountType;
	}

}
