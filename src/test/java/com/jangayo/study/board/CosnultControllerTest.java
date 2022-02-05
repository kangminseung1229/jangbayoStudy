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

    @DisplayName("리팩토링 - 상담글 입력 - 정상")
    @Test
    void consultInsert_correct_RE () throws Exception {

        mockMvc.perform(post("/consult/consult-detail")
                        .param("userid", "jangbayo")
                        .param("consultTitle", "장바요 프로젝트입니다.")
                        .param("consultText", "화이팅.")
                )
                .andExpect(status().is3xxRedirection());
    }

    
    @DisplayName("리팩토링 - 상담글 입력 - 오류")
    @Test
    void consultInsert_wrong_RE () throws Exception {

        mockMvc.perform(post("/consult/consult-detail")
                .param("userid", "sdssdssds") 
                .param("consultTitle", "") //title 공백
                .param("consultText", "testText")
                )
                .andExpect(status().isOk());
    }


    @DisplayName("리팩토링 - 상담글 수정 - 정상")
    @Test
    void consultUpdate_corrent_RE () throws Exception{
        mockMvc.perform(post("/consult/consult-detail")
                        .param("id", "209")
                        .param("userid", "kangminseung")
                        .param("consultTitle", "리팩토링 수정된 제목입니다.")
                        .param("consultText", "리팩토링 과정이 어떻게 되나요?")
                        )
                        .andExpect(status().is3xxRedirection());
    }


    
    @DisplayName("리팩토링 - 상담글 수정 - 오류")
    @Test
    void consultUpdate_wrong_RE () throws Exception{

        mockMvc.perform(post("/consult/consult-detail")
                        .param("id", "209")
                        .param("userid", "kangminseung")
                        .param("consultTitle", "")
                        .param("consultText", "")
                        )
                        .andExpect(status().isOk());
    }


    
}
