package com.tr.kahveciefendi.app.model;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
*
* Item JPA entity
*
*/
@Entity
@Table(name = "ITEMS")
@NamedQueries({
	@NamedQuery(
	        name = Item.FIND_BY_ITEMNAME,
	        query = "select i from Item i where name = :itemname"
	),
	@NamedQuery(
	        name = Item.FIND_ALL_ITEMS,
	        query = "select i from Item i"
	)
})
public class Item extends AbstractEntity {
	
	public static final String FIND_BY_ITEMNAME = "item.findByItemName";
	public static final String FIND_ALL_ITEMS = "item.findAllItems";
	
	private String name;
	private ItemType itemType;
	private BigDecimal price;
	
	public Item() {

    }
	
	public Item(String name, ItemType itemType, BigDecimal price) {
		this.name = name;
		this.itemType = itemType;
		this.price = price;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public ItemType getItemType() {
		return itemType;
	}
	public void setItemType(ItemType itemType) {
		this.itemType = itemType;
	}
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	

}
