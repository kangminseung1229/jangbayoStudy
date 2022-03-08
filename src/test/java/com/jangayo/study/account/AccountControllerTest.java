package com.jangayo.study.account;

import javax.transaction.TransactionScoped;

import org.apache.tomcat.util.net.DispatchType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class AccountControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @DisplayName("회원가입 - 성공")
    @Test
    public void singUpForm_success() throws Exception {

        mockMvc.perform(post("/account/sign-up")
                .param("nickname", "tete") 
                .param("email", "email@email.com")
                .param("password", "12345678")
                .with(csrf()))
                .andExpect(status().is3xxRedirection());

    }

    @DisplayName("회원가입 - 오류")
    @Test
    public void singUpForm_fail() throws Exception {

        mockMvc.perform(post("/account/sign-up")
                .param("nickname", "t") // 길이 미달
                .param("email", "email@email.com")
                .param("password", "18") //길이 미달
                .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(model().hasErrors());
    }

}
