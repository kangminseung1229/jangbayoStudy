package com.jangayo.study.account;

import java.util.List;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/account")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;
    private final AccountRepository accountRepository;
    private final AccountRoleRepository accountRoleRepository;

    //회원가입창
    @GetMapping("/sign-up")
    public String signUpPage(){
        return "login/sign-up";
    }

    //회원가입처리
    @PostMapping("/sign-up")
    public String signUp(SignUpForm signUpForm){
        accountService.signUp(signUpForm);
        return "redirect:/";
    }

    //권한확인
    @GetMapping("/principal")
    public String principal(){  
        return "login/principal";
    }

    //권한편집 페이지
    @GetMapping("/principal-modify")
    public String principalModify(@RequestParam(required = false) String searchText, Model model){


        if (searchText != null) {
            Account searchAccount = accountRepository.findByNickname(searchText);
            model.addAttribute("auth", searchAccount.getRoles());
        } else {
            model.addAttribute("auth", "아이디를 넣어주세요!");
        }


        model.addAttribute("allAuth", accountRoleRepository.findAll());

        return "account/principal-modify";
    }
    
}



