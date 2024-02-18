package com.tr.kahveciefendi.app.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.tr.kahveciefendi.app.dto.ReportItemDTO;
import com.tr.kahveciefendi.app.model.Order;

@Repository
public class OrderRepository {
	
	private static final Logger LOGGER = Logger.getLogger(OrderRepository.class);

    @PersistenceContext
    EntityManager em;
    
    public Order save(Order order){
    	Order saved = em.merge(order);
    	
    	LOGGER.info("Order saved.");
    	
    	return saved;
    	
    }
    
    public List<ReportItemDTO> getUserOrderTotals(){
    	List<ReportItemDTO> reportItems = new ArrayList<ReportItemDTO>();
    	
    	List<Object[]> orders = em.createNamedQuery(Order.GET_USER_TOTALS).getResultList();
    	
    	for (Object[] object: orders){
    		ReportItemDTO reportItemDTO = new ReportItemDTO();
    		for (Object inner: object){
   				reportItemDTO.addToFields(String.valueOf(inner));
    		}
    		
    		reportItems.add(reportItemDTO);
    		
    	}
    	
    	return reportItems;
    }
    
    public List<ReportItemDTO> getBeverageOrderTotals(){
		List<ReportItemDTO> reportItems = new ArrayList<ReportItemDTO>();

		List<Object[]> orders = em.createNamedQuery(Order.GET_BEVERAGE_TOTALS).getResultList();

		for (Object[] object : orders) {
			ReportItemDTO reportItemDTO = new ReportItemDTO();
			for (Object inner : object) {
				reportItemDTO.addToFields(String.valueOf(inner));
			}

			reportItems.add(reportItemDTO);

		}

		return reportItems;
    }
    
    /**
     * 
     * Get order by user
     *
     */
    public List<Order> getOrdersByUserId(Long userid){
    	List<Order> orders = em.createNamedQuery(Order.FIND_BY_USERID, Order.class)
    			.setParameter("userid", userid)
                .getResultList();
    	
    	return orders;
    }

}
