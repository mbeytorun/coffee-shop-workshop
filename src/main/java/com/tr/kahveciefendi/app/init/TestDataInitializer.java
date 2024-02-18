package com.tr.kahveciefendi.app.init;


import java.math.BigDecimal;

import javax.persistence.EntityManagerFactory;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tr.kahveciefendi.app.model.Item;
import com.tr.kahveciefendi.app.model.ItemType;
import com.tr.kahveciefendi.app.model.User;

/**
 *
 * Inserts some test data into db.
 *
 */
@Component
public class TestDataInitializer {

    @Autowired
    private EntityManagerFactory entityManagerFactory;


    public void init() throws Exception {

        SessionFactory sessionFactory = entityManagerFactory.unwrap(SessionFactory.class);

        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        User user = new User("test123", "$2a$10$x9vXeDsSC2109FZfIJz.pOZ4dJ056xBpbesuMJg3jZ.ThQkV119tS", "test@email.com");

        session.persist(user);
        
        session.persist(new Item("Filtre Kahve", ItemType.Beverage, new BigDecimal(4)));
        session.persist(new Item("Latte", ItemType.Beverage, new BigDecimal(5)));
        session.persist(new Item("Mocha", ItemType.Beverage, new BigDecimal(6)));
        session.persist(new Item("Çay", ItemType.Beverage, new BigDecimal(3)));
        session.persist(new Item("Türk Kahvesi", ItemType.Beverage, new BigDecimal(5)));
        
        session.persist(new Item("Süt", ItemType.Addition, new BigDecimal(2)));
        session.persist(new Item("Fındık Şurubu", ItemType.Addition, new BigDecimal(3)));
        session.persist(new Item("Çikolata Sosu", ItemType.Addition, new BigDecimal(5)));
        session.persist(new Item("Limon", ItemType.Addition, new BigDecimal(2)));

        transaction.commit();
    }
}
