package com.jangayo.study.account;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.TestExecutionEvent;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.xmlunit.diff.RecursiveXPathBuilder;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;
import java.util.List;

import org.aspectj.lang.annotation.After;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class AccountControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private AccountService accountService;

    @Autowired
    private AccountRoleRepository accountRoleRepository;

    @BeforeEach
    private void beforeEach() {
        
        System.out.println("BEFORE======================");
        SignUpForm signUpForm = new SignUpForm();
        signUpForm.setNickname("test");
        signUpForm.setEmail("test@email.com");
        signUpForm.setPassword("12345678");
        accountService.signUp(signUpForm);
        System.out.println("BEFORE======================");
        
    }
    
    @AfterEach
    private void AfterEach(){
        System.out.println("AFTER======================");
    }



    @WithUserDetails(value = "test", setupBefore = TestExecutionEvent.TEST_EXECUTION)
    @DisplayName("권한 수정")
    @Test
    public void privateModify() throws Exception{

        String nickname = "test";

        List<AccountRole> roles = accountRoleRepository.findAll();

        List<Long> rolesValue = new ArrayList<>();

        for (AccountRole role : roles){
            rolesValue.add(role.getId());
        }

        mockMvc.perform(post("/account/principal-modify/" + nickname)
                        .content(rolesValue.toString())
                        .contentType("application/json")
                        .param("nickname", nickname)
                        .with(csrf())
                        )
                        .andExpect(status().isOk());
    }




    @DisplayName("이메일 발송 - 오류")
    @Test
    public void emailSend_fail() throws Exception{

        mockMvc.perform(get("/account/check-email-token")
                        .param("token", "adadsd")
                        .param("email", "dafadfa@adfad.com")
                        )
                        .andExpect(view().name("account/check-email-success"))
                        .andExpect(model().attributeExists("error"))
                        .andExpect(status().isOk())
                        .andExpect(unauthenticated());
    }

    @DisplayName("이메일 발송 - 성공")
    @Test
    public void checkEmailToken() throws Exception{

        Account account = Account.builder()
                        .email("test@email.com")
                        .password("12341234")
                        .nickname("kang")
                        .build();
        
        Account newAccount =  accountRepository.save(account);
        newAccount.generateEmailCheckToken();

        mockMvc.perform(get("/account/check-email-token")
                        .param("token", newAccount.getEmailCheckToken())
                        .param("email", newAccount.getEmail())
        )
                        .andExpect(status().isOk())
                        .andExpect(model().attributeExists("nickname"))
                        .andExpect(model().attributeExists("cnt"))
                        .andExpect(model().attributeDoesNotExist("error"))
                        .andExpect(authenticated().withUsername("kang"));
    }

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
