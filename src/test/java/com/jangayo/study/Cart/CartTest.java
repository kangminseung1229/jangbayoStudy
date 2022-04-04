package com.jangayo.study.Cart;

import static com.jangayo.study.cart.CartController.CART;
import static com.jangayo.study.cart.CartController.ROOT;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jangayo.study.account.AccountRepository;
import com.jangayo.study.account.AccountService;
import com.jangayo.study.account.SignUpForm;
import com.jangayo.study.cart.form.OptionForm;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.test.context.support.TestExecutionEvent;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;


@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class CartTest {

    @Autowired private MockMvc mockMvc;
    @Autowired private AccountService accountService;
    @Autowired private AccountRepository accountRepository;
    @Autowired private ObjectMapper objectMapper;

    //Test ID 생성
    @BeforeEach
    private void beforeEach(){
        SignUpForm signUpForm = SignUpForm.builder()
                        .nickname("test1")
                        .email("test1.aaa.com")
                        .password("12341234")
                        .build();
        accountService.signUp(signUpForm);
    }

    @DisplayName("장바구니 권한 진입")
    @Test
    @WithUserDetails(value = "test1", setupBefore = TestExecutionEvent.TEST_EXECUTION)
    void cartPage() throws Exception {

        mockMvc.perform(get("/cart/cart-list"))
                .andExpect(status().isOk());
    }

    @DisplayName("장바구니 담기")
    @Test
    @WithUserDetails(value = "test1", setupBefore = TestExecutionEvent.TEST_EXECUTION)
    void addCartItem() throws Exception{

        // Account account = accountRepository.findByNickname("test1");

        OptionForm optionForm = OptionForm.builder()
                                .optionId(6l)
                                .cnt(10)
                                .build();

        mockMvc.perform(post(ROOT + CART + "/init-cart")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(optionForm))
                    .with(csrf())
        )
                    .andExpect(status().isOk())
                    .andExpect(authenticated().withUsername("test1"));
                    
    }
    
}
