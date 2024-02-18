package com.tr.kahveciefendi.app;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.tr.kahveciefendi.app.dto.DiscountType;
import com.tr.kahveciefendi.config.root.RootContextConfig;
import com.tr.kahveciefendi.config.root.TestConfiguration;
import com.tr.kahveciefendi.config.servlet.ServletContextConfig;

import sun.security.acl.PrincipalImpl;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ActiveProfiles("test")
@ContextConfiguration(classes={TestConfiguration.class, RootContextConfig.class, ServletContextConfig.class})
public class OrderRestWebServiceTest {
	
	private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext wac;

    @Before
    public void init()  {
        mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }
    
    @Test
    public void testSaveOrder() throws Exception {
    	String content = "{\"id\":1,\"user\":{\"id\":1,\"userName\":\"test123\"},\"orderItems\":[{\"beverage\":"
    			+ "{\"id\":2,\"name\":\"Latte\",\"itemType\":\"Beverage\",\"price\":5,\"selected\":false},"
    			+ "\"additions\":[{\"id\":8,\"name\":\"Çikolata Sosu\",\"itemType\":\"Addition\",\"price\":5,"
    			+ "\"selected\":false}],\"totalPrice\":15},{\"beverage\":{\"id\":3,\"name\":\"Mocha\","
    			+ "\"itemType\":\"Beverage\",\"price\":6,\"selected\":false},\"totalPrice\":8,\"additions\""
    			+ ":[{\"id\":6,\"name\":\"Süt\",\"itemType\":\"Addition\",\"price\":2,\"selected\":false}]}],\""
    			+ "totalAmount\":23,\"paidAmount\":0,\"finalDiscount\":0}";
    	
    	mockMvc.perform(post("/order")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content)
                .accept(MediaType.APPLICATION_JSON)
                .principal(new PrincipalImpl(UserServiceTest.USERNAME)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
    			.andExpect(jsonPath("$.['id']").value(1));
    }
    
    @Test
    public void testCalculateDiscount() throws Exception {
    	//String content = "{\"id\":1, \"user\": {\"id\":1,\"userName\":\"yok\"}, \"orderItems\":[], \"date\":\"2015/01/01\",\"time\": \"11:00\", "
        	//	+ "\"totalAmount\":\"100\", \"paidAmount\": \"75\", \"finalDiscount\": \"25\"}";
    	
    	String content = "{\"id\":1,\"user\":{\"id\":1,\"userName\":\"test123\"},\"orderItems\":[{\"beverage\":"
    			+ "{\"id\":2,\"name\":\"Latte\",\"itemType\":\"Beverage\",\"price\":5,\"selected\":false},"
    			+ "\"additions\":[{\"id\":8,\"name\":\"Çikolata Sosu\",\"itemType\":\"Addition\",\"price\":5,"
    			+ "\"selected\":false}],\"totalPrice\":15},{\"beverage\":{\"id\":3,\"name\":\"Mocha\","
    			+ "\"itemType\":\"Beverage\",\"price\":6,\"selected\":false},\"totalPrice\":8,\"additions\""
    			+ ":[{\"id\":6,\"name\":\"Süt\",\"itemType\":\"Addition\",\"price\":2,\"selected\":false}]}],\""
    			+ "totalAmount\":23,\"paidAmount\":0,\"finalDiscount\":0}";
    	
    	mockMvc.perform(put("/order")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content)
                .accept(MediaType.APPLICATION_JSON)
                .principal(new PrincipalImpl(UserServiceTest.USERNAME)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))	
    			.andExpect(jsonPath("$.['discountType']").value("WholeBasketDiscount"));

    }

}
