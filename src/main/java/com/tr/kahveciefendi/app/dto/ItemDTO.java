package com.tr.kahveciefendi.app.dto;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import com.tr.kahveciefendi.app.model.Item;
import com.tr.kahveciefendi.app.model.ItemType;

public class ItemDTO {
	
	private Long id;
	private String name;
	private ItemType itemType;
	private BigDecimal price;
	
	public ItemDTO(){
		
	}
	
	public ItemDTO(Long id, String name, ItemType itemType, BigDecimal price) {
		this.id = id;
		this.name = name;
		this.itemType = itemType;
		this.price = price;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
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
	
	public static ItemDTO mapFromItemEntity(Item item) {
		return new ItemDTO(item.getId(), item.getName(), item.getItemType(), item.getPrice());
	}

	public static List<ItemDTO> mapFromItemEntities(List<Item> items){
		return items.stream().map((item) -> mapFromItemEntity(item)).collect(Collectors.toList());
	}

}
