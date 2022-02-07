package com.jangayo.study.board;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
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


    @DisplayName("답변글 작성 페이지 진입")
    @Test
    void consultAnswer_Page() throws Exception{

        mockMvc.perform(get("/consult/consult-answer")
                    .param("id", "213")                
                        )
                    .andExpect(status().isOk());
    }

    @DisplayName("답변글 작성 - 오류")
    @Test
    void consultAnswer_wrong() throws Exception{

        mockMvc.perform(post("/consult/consult-answer")
                    .param("id", "213")
                    .param("answerTitle","")
                    .param("answerText","우리는스프링부트입니다.")
                    .param("answerTime", LocalDateTime.now().toString())
        )
                .andExpect(status().isOk());
    }

    @DisplayName("답변글 작성 - 정상")
    @Test
    void consultAnswer_correct() throws Exception{
        
        mockMvc.perform(post("/consult/consult-answer")
                    .param("id", "209")
                    .param("answerTitle","답변드립니다.")
                    .param("answerText","테스트 답변 글 작성 시 rollback")
                    .param("answerTime", LocalDateTime.now().toString())
        )
                .andExpect(status().is3xxRedirection());
        
    }
    
}
