package com.tr.kahveciefendi.app.model;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
*
* Order Item JPA entity
*
*/
@Entity
@Table(name = "ORDER_ITEMS")
public class OrderItem extends AbstractEntity {
	
	@ManyToOne
	private Order order;
	
	@ManyToOne(cascade=CascadeType.DETACH)
	private Item beverage;
	
	@ManyToMany(cascade=CascadeType.DETACH)
	private List<Item> additions;
	
	public OrderItem(){
		
	}
	
	public OrderItem(Order order, Item beverage, List<Item> additions) {
		this.order = order;
		this.beverage = beverage;
		this.additions = additions;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public Item getBeverage() {
		return beverage;
	}

	public void setBeverage(Item beverage) {
		this.beverage = beverage;
	}

	public List<Item> getAdditions() {
		return additions;
	}

	public void setAdditions(List<Item> additions) {
		this.additions = additions;
	}

	public BigDecimal getPrice() {
		BigDecimal additionsTotal = BigDecimal.ZERO;
		
		for (Item addition: this.additions){
			additionsTotal = additionsTotal.add(addition.getPrice());
		}
		BigDecimal totalPrice = this.beverage.getPrice().add(additionsTotal);
		return totalPrice;
	}

}
