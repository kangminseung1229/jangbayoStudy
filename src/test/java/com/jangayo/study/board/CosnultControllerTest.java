package com.jangayo.study.board;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.then;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class CosnultControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ConsultRepository consultRepository;

    @DisplayName("상담글만 수정 - 정상")
    @Test
    void consultUpdate_correct() throws Exception{

        mockMvc.perform(post("/consult/consult-detail")
                .param("id", "202")
                .param("userid", "tempUser")
                .param("consultTitle", "수정된 상담 제목")
                .param("consultText", "수정된 상담 글")
            )
            .andExpect(status().is3xxRedirection());

    }


    
}
