package com.tr.kahveciefendi.app.dto;

import java.util.List;

public class ItemsDTO {
	
	private List<ItemDTO> items;
	
	public ItemsDTO(){
		
	}
	
	public ItemsDTO(List<ItemDTO> items){
		this.items = items;
	}

	public List<ItemDTO> getItems() {
		return items;
	}

	public void setItems(List<ItemDTO> items) {
		this.items = items;
	}
	
	

}
