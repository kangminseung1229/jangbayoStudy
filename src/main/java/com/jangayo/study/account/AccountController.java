package com.jangayo.study.account;

import java.util.List;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/account")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;
    private final AccountRepository accountRepository;
    private final AccountRoleRepository accountRoleRepository;
    private final SignUpFormValidator signUpFormValidator;

    @InitBinder("signUpForm")
    public void InitBinder(WebDataBinder webDataBinder) {
        webDataBinder.addValidators(signUpFormValidator);
    }

    //회원가입창
    @GetMapping("/sign-up")
    public String signUpPage( Model model){
        model.addAttribute("signUpForm", new SignUpForm());
        return "login/sign-up";
    }

    //회원가입처리
    @PostMapping("/sign-up")
    public String signUp(@Valid SignUpForm signUpForm, Errors errors){


        if (errors.hasErrors()) {
            return "login/sign-up";
        }

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
            model.addAttribute("message", "아이디를 넣어주세요!");
        }


        model.addAttribute("allAuth", accountRoleRepository.findAll());
        model.addAttribute("searchText", searchText);

        return "account/principal-modify";
    }

    //권한 수정
    @PostMapping("/principal-modify/{nickname}")
    @ResponseBody
    public Account principalUpdate( @RequestBody List<Long> authorities, @PathVariable String nickname){
        return accountService.principalUpdate(authorities, nickname);
    }

    
}



