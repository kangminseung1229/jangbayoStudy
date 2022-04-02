package com.jangayo.study.Cart;

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;



@SpringBootTest
@AutoConfigureMockMvc
public class CartTest {


    
    @Autowired
    private MockMvc mockMvc;


    @AfterEach
    void deleteSql (){
        
        
    }


    @Test
    void test1() throws Exception {

        mockMvc.perform(get("/"))
                .andExpect(status().isOk());
    }
    
}
