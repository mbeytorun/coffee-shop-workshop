package com.tr.kahveciefendi.app;

import static com.tr.kahveciefendi.app.TestUtils.date;
import static com.tr.kahveciefendi.app.TestUtils.time;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.tr.kahveciefendi.app.dto.ReportItemDTO;
import com.tr.kahveciefendi.app.model.Item;
import com.tr.kahveciefendi.app.model.Order;
import com.tr.kahveciefendi.app.model.OrderItem;
import com.tr.kahveciefendi.app.model.User;
import com.tr.kahveciefendi.app.services.ItemService;
import com.tr.kahveciefendi.app.services.OrderService;
import com.tr.kahveciefendi.app.services.UserService;
import com.tr.kahveciefendi.config.root.RootContextConfig;
import com.tr.kahveciefendi.config.root.TestConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("test")
@ContextConfiguration(classes={TestConfiguration.class, RootContextConfig.class})
public class OrderServiceTest {
	
	@Autowired
	OrderService orderService;
	
	@Autowired
	UserService userService;
	
	@Autowired
	ItemService itemService;

	@Test
	public void testSaveOrder(){
		User user = userService.findUserByUsername(UserServiceTest.USERNAME);
		
		List<OrderItem> orderItems = new ArrayList<>();
				
		Order order = new Order(user, orderItems, date(2016,5,5), time("12:00"), new BigDecimal(35), new BigDecimal(30));
		
		Item beverage = itemService.findItemById(2L);
		Item beverage2 = itemService.findItemById(1L);
		Item addition = itemService.findItemById(6L);
		Item addition2 = itemService.findItemById(7L);
	
		List<Item> additions = new ArrayList<>();
		additions.add(addition);
		additions.add(addition2);
		 
		OrderItem orderItem = new OrderItem(order, beverage2, additions);
		OrderItem orderItem2 = new OrderItem(order, beverage, additions);
		OrderItem orderItem3 = new OrderItem(order, beverage, additions);
		orderItems.add(orderItem);
		orderItems.add(orderItem2);
		orderItems.add(orderItem3);
		
		orderService.calculateDiscount(order);
		assertTrue(order.getPaidAmount().toString().equals("20.00"));		
		
		orderService.save(order);
		
		List<Order> orders = orderService.getOrdersByUserId(1L);
		assertTrue(orders.get(0).getOrderItems().get(0).getBeverage().getName().equals("Latte"));
		
	}
		
	@Test
	public void testGetBeverageOrderTotals(){
		List<ReportItemDTO> orders = orderService.getBeverageOrderTotals();
		System.out.println(orders.size());
		assertTrue(orders.size()==2);		
	}
	
	@Test
	public void testGetUserOrderTotals(){
		List<ReportItemDTO> orders = orderService.getUserOrderTotals();
		System.out.println(orders.size());
		assertTrue(orders.size()==1);		
	}

}
