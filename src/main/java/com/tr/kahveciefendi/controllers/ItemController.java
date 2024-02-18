package com.tr.kahveciefendi.controllers;

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

import com.tr.kahveciefendi.app.dto.ItemDTO;
import com.tr.kahveciefendi.app.dto.ItemsDTO;
import com.tr.kahveciefendi.app.model.Item;
import com.tr.kahveciefendi.app.services.ItemService;

/**
*
*  REST service for users.
*
*/

@Controller
@RequestMapping("/item")
public class ItemController {

	private Logger LOGGER = Logger.getLogger(ItemController.class);
	
	@Autowired
	private ItemService itemService;
	
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(method = RequestMethod.GET)
	public ItemsDTO getAllItems(){
		List<Item> items = itemService.getAllItems();
		
		return new ItemsDTO(ItemDTO.mapFromItemEntities(items));
	}
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(method = RequestMethod.POST)
	public void createItem(@RequestBody ItemDTO item) {
		itemService.createItem(item.getName(), item.getItemType(), item.getPrice());
	}
	
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(method = RequestMethod.DELETE)
	public void deleteItems(@RequestBody List<Long> itemIds){
		itemService.deleteItems(itemIds);
		
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
