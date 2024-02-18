package com.tr.kahveciefendi.app.services;

import java.math.BigDecimal;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tr.kahveciefendi.app.dao.OrderRepository;
import com.tr.kahveciefendi.app.dto.DiscountType;
import com.tr.kahveciefendi.app.dto.ReportItemDTO;
import com.tr.kahveciefendi.app.model.Order;
import com.tr.kahveciefendi.app.model.OrderItem;

@Service
public class OrderService {
	
	private static final Logger LOGGER = Logger.getLogger(OrderService.class);
	
	@Autowired
	private OrderRepository orderRepository;
	
	public List<ReportItemDTO> getUserOrderTotals(){
		return orderRepository.getUserOrderTotals();
	}
	
	public List<ReportItemDTO> getBeverageOrderTotals(){
		return orderRepository.getBeverageOrderTotals();
	}
	
	@Transactional
	public Order save(Order order){
		return orderRepository.save(order);
	}
	
	@Transactional(readOnly = true)
	public List<Order> getOrdersByUserId(Long userId) {
		return orderRepository.getOrdersByUserId(userId);
	}
	
	@Transactional(readOnly = true)
	public void calculateDiscount(Order order){
		setTotalAmount(order);
		
		BigDecimal wholeBasketDiscount = calculateWholeBasketDiscount(order);
		BigDecimal cheapestOneFreeDiscount = calculateCheapestOneFreeDiscount(order);
		
		if (wholeBasketDiscount.compareTo(BigDecimal.ZERO)==0 && cheapestOneFreeDiscount.compareTo(BigDecimal.ZERO)==0){
			order.setPaidAmount(order.getTotalAmount());
			order.setDiscountType(DiscountType.None);
			return;
		}
		
		if (wholeBasketDiscount.compareTo(cheapestOneFreeDiscount)>0){
			order.setPaidAmount(order.getTotalAmount().subtract(wholeBasketDiscount));
			order.setDiscountType(DiscountType.WholeBasketDiscount);
		}else{
			order.setPaidAmount(order.getTotalAmount().subtract(cheapestOneFreeDiscount));
			order.setDiscountType(DiscountType.CheapestOneFreeDiscount);
		}

	}

	private BigDecimal calculateCheapestOneFreeDiscount(Order order) {
		if (order.getOrderItems().size()>2){
			BigDecimal cheapest = order.getOrderItems().get(0).getPrice();

			for (OrderItem orderItem: order.getOrderItems()){
				if (orderItem.getPrice().compareTo(cheapest)<0)
					cheapest = orderItem.getPrice();
			}
			
			return cheapest;
		}
		
		return BigDecimal.ZERO;
	}

	private BigDecimal calculateWholeBasketDiscount(Order order) {
		
		if (order.getTotalAmount().compareTo(new BigDecimal(12))>0){
			return order.getTotalAmount().multiply(new BigDecimal(0.25));
		}

		return BigDecimal.ZERO;
	}
	
	private void setTotalAmount(Order order) {
		BigDecimal totalAmount = BigDecimal.ZERO;
		for (OrderItem orderItem: order.getOrderItems()){
			totalAmount = totalAmount.add(orderItem.getPrice());
		}
		
		order.setTotalAmount(totalAmount);
	}
	
	

}
