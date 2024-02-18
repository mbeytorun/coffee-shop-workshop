package com.tr.kahveciefendi.app.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.tr.kahveciefendi.app.model.Item;

@Repository
public class ItemRepository {
	
	private static final Logger LOGGER = Logger.getLogger(ItemRepository.class);

    @PersistenceContext
    EntityManager em;
    
    /**
     * 
     * Get all items
     *
     */
    public List<Item> getAllItems(){
    	return em.createNamedQuery(Item.FIND_ALL_ITEMS, Item.class).getResultList();
    }
    
    /**
     * 
     * Deletes an item
     *
     */
    public void delete(Long itemId) {
        Item delete = em.find(Item.class, itemId);
        em.remove(delete);
    }

    /**
     *
     * Finds an item given its id
     *
     */
    public Item findItemById(Long id) {
        return em.find(Item.class, id);
    }

    /**
     *
     * Save changes made to an item, or create the item if its new
     *
     */
    public Item save(Item item) {
        return em.merge(item);
    }
    
    /**
     * checks if an item is already in the database
     *
     * @param itemName - the item to be checked for availability
     * @return true if the itemName is still available
     */
    public boolean isItemAvailable(String itemName) {

        List<Item> items = em.createNamedQuery(Item.FIND_BY_ITEMNAME, Item.class)
                .setParameter("itemname", itemName)
                .getResultList();

        return items.isEmpty();
    }

}
