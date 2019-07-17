package com.ft.test;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

/** 
 * @author zhzh 
 * 2015-4-7 
 */  
@RunWith(SpringJUnit4ClassRunner.class)    
@WebAppConfiguration    
@ContextConfiguration({"classpath*:/META-INF/spring/*.xml"})
//当然 你可以声明一个事务管理 每个单元测试都进行事务回滚 无论成功与否    
//@TransactionConfiguration(defaultRollback = true)    
//@Transactional   
public class BaseTest {
    @Autowired    
    protected WebApplicationContext wac;

    protected MockMvc mockMvc;
      
    @Before
    public void setup() {     
        this.mockMvc = webAppContextSetup(this.wac).build();  
    }

}