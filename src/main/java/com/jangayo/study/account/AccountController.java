package com.jangayo.study.account;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/account")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;
    private final AccountRoleRepository  accountRoleRepository;

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

    //권한 수정 페이지
    @GetMapping("/principal-modify")
    public String principalUpdatePage(Model model){


        List<AccountRole> AllRoles = accountRoleRepository.findAll();


        String userRoles = accountRoleRepository.getRoles(4l).toString(); 
        


        model.addAttribute("AllRoles",AllRoles);
        model.addAttribute("userRoles",userRoles);


        return "admin/principalModify";
    }
    
}



