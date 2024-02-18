package com.tr.kahveciefendi.controllers;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.tr.kahveciefendi.app.dto.DiscountDTO;
import com.tr.kahveciefendi.app.dto.OrderDTO;
import com.tr.kahveciefendi.app.model.Item;
import com.tr.kahveciefendi.app.model.Order;
import com.tr.kahveciefendi.app.model.OrderItem;
import com.tr.kahveciefendi.app.model.User;
import com.tr.kahveciefendi.app.services.ItemService;
import com.tr.kahveciefendi.app.services.OrderService;
import com.tr.kahveciefendi.app.services.UserService;

@Controller
@RequestMapping("/order")
public class OrderController {
	
	private Logger LOGGER = Logger.getLogger(OrderController.class);
	
	@Autowired
	ItemService itemService;
		
	@Autowired
	OrderService orderService;
	
	@Autowired
	UserService userService;
	
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(method = RequestMethod.POST)
	public OrderDTO save(@RequestBody OrderDTO order) {
		User user = userService.getUserById(order.getUser().getId());
		
		readItems(order.getOrderItems());
		
		Order orderToSave = new Order(user, order.getOrderItems(), order.getDate(), 
				order.getTime(), new BigDecimal(order.getTotalAmount()), 
				new BigDecimal(order.getPaidAmount()));
				
		orderService.calculateDiscount(orderToSave);
		
		Order saved = orderService.save(orderToSave);
		
		order.setId(saved.getId());
		order.setTotalAmount(saved.getTotalAmount().toString());
		order.setPaidAmount(saved.getPaidAmount().toString());
		order.setDiscountType(saved.getDiscountType());
		
		return order;

	}
	
	private List<OrderItem> readItems(List<OrderItem> orderItems) {
		List<OrderItem> orderItemsRead = new ArrayList<OrderItem>();
		for (OrderItem orderItem: orderItems){
			
			Item beverage = itemService.findItemById(orderItem.getBeverage().getId());
			orderItem.setBeverage(beverage);
			
			List<Item> additions = new ArrayList<Item>();
			for (Item additionItem: orderItem.getAdditions()){
				Item addition = itemService.findItemById(additionItem.getId());
				additions.add(addition);
			}
			orderItem.setAdditions(additions);
			
			orderItemsRead.add(orderItem);
		}
		
		return orderItemsRead;
		
	}

	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(method = RequestMethod.PUT)
	public DiscountDTO calculateDiscount(@RequestBody OrderDTO order){
		
		Order orderModel = new Order(null, order.getOrderItems(), order.getDate(), 
				order.getTime(), new BigDecimal(order.getTotalAmount()), new BigDecimal(order.getPaidAmount()));
		
		orderService.calculateDiscount(orderModel);
		
		DiscountDTO discountDTO = new DiscountDTO();
		discountDTO.setPaidAmount(orderModel.getPaidAmount().toString());
		discountDTO.setTotalAmount(orderModel.getTotalAmount().toString());
		discountDTO.setDiscountType(orderModel.getDiscountType());
		
		return discountDTO;
	}
	
	/**
    *
    * error handler for backend errors - a 400 status code will be sent back, and the body
    * of the message contains the exception text.
    *
    * @param exc - the exception caught
    */

   @ExceptionHandler(Exception.class)
   public ResponseEntity<String> errorHandler(Exception exc) {
       LOGGER.error(exc.getMessage(), exc);
       return new ResponseEntity<>(exc.getMessage(), HttpStatus.BAD_REQUEST);
   }

}
