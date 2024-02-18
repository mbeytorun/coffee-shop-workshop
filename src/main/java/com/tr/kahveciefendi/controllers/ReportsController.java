package com.tr.kahveciefendi.controllers;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.tr.kahveciefendi.app.dto.ReportItemDTO;
import com.tr.kahveciefendi.app.dto.ReportItemsDTO;
import com.tr.kahveciefendi.app.services.OrderService;

@Controller
public class ReportsController {
	
private Logger LOGGER = Logger.getLogger(OrderController.class);
	
	@Autowired
	OrderService orderService;
	
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping("/userTotals")
	public ReportItemsDTO getUserOrderTotals(){
		List<ReportItemDTO> reportItems = orderService.getUserOrderTotals();
		ReportItemsDTO reportItemsDTO = new ReportItemsDTO(reportItems);
		
		return reportItemsDTO;
		
	}
	
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping("/beverageTotals")
	public ReportItemsDTO getBeverageOrderTotals(){
		List<ReportItemDTO> reportItems = orderService.getBeverageOrderTotals();
		ReportItemsDTO reportItemsDTO = new ReportItemsDTO(reportItems);
		
		return reportItemsDTO;
		
	}

}
