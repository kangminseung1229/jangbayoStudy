package com.jangayo.study.thymeleaf;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.then;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("dev")
public class THcontrollertest {

    @Autowired
    private MockMvc mockMvc;

    @DisplayName("Page1 진입 - 정상")
    @Test
    public void page1_ok() throws Exception{

        mockMvc.perform(get("/page1"))
            .andExpect(status().isOk())
            .andExpect(view().name("thymeleaf/page1"));
        
    }

    @DisplayName("Page2 진입 - 정상")
    @Test
    public void page2_ok() throws Exception{


        mockMvc.perform(post("/page2")
        .param("id", "1")
        .param("title", "1")
        .param("memo", "1")
        .param("created", "1")
        .param("updated", "1")
        )
        .andExpect(status().isOk())
        .andExpect(view().name("thymeleaf/page2"));


    }

    
    
}
