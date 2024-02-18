package com.tr.kahveciefendi.app.services;

import static org.springframework.util.Assert.notNull;

import java.math.BigDecimal;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tr.kahveciefendi.app.dao.ItemRepository;
import com.tr.kahveciefendi.app.model.Item;
import com.tr.kahveciefendi.app.model.ItemType;

@Service
public class ItemService {
	
	  private static final Logger LOGGER = Logger.getLogger(ItemService.class);
	  
	  @Autowired
	  private ItemRepository itemRepository; 
	  
	  @Transactional(readOnly = true)
	  public List<Item> getAllItems(){
		  return itemRepository.getAllItems();
	  }
	  
	  @Transactional
	  public Item createItem(String name, ItemType itemType, BigDecimal price){
		  if (!itemRepository.isItemAvailable(name)) {
	            throw new IllegalArgumentException("There is an item already with this name.");
	       }
		  
		  Item item = new Item(name, itemType, price);
		  Item saved = itemRepository.save(item);
		  
		  LOGGER.info("Item " + saved.getName() + " created by id " + saved.getId());
		  
		  return saved;
	  }
	  
	  @Transactional(readOnly = true)
	  public Item findItemById(Long id){
		  return itemRepository.findItemById(id);
	  }
	  
	  @Transactional
	  public void deleteItems(List<Long> itemIds){
		  notNull(itemIds, "deletedMealsId is mandatory");
		  itemIds.stream().forEach((itemId) -> itemRepository.delete(itemId));
	  }

}
