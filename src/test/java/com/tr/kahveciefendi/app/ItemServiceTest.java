package com.tr.kahveciefendi.app;

import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.tr.kahveciefendi.app.model.Item;
import com.tr.kahveciefendi.app.model.ItemType;
import com.tr.kahveciefendi.app.services.ItemService;
import com.tr.kahveciefendi.config.root.RootContextConfig;
import com.tr.kahveciefendi.config.root.TestConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("test")
@ContextConfiguration(classes={TestConfiguration.class, RootContextConfig.class})
public class ItemServiceTest {
	
	@Autowired
	private ItemService itemService;
	
	@Test
	public void testGetAllItems(){
		List<Item> items = itemService.getAllItems();
		assertTrue(items.get(0).getName().equals("Filtre Kahve"));
	}
	
	@Test
	public void testCreateItem(){
		Item newItem = itemService.createItem("Test Beverage", ItemType.Beverage, new BigDecimal(5));
		assertTrue(newItem.getName().equals("Test Beverage"));
	}
	
	@Test
	public void findItemById(){
		Item item = itemService.findItemById(1L);
		assertTrue(item.getName().equals("Filtre Kahve"));
	}

}
