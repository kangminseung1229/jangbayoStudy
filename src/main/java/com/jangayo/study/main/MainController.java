package com.jangayo.study.main;

import com.jangayo.study.account.Account;
import com.jangayo.study.account.CurrentUser;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class MainController {

    @GetMapping("/")
    public String index(@CurrentUser Account account, Model model){

        if (account != null) {
            model.addAttribute(account);
        }
        
        return "index";
    }

    @GetMapping("/login")
    public String login(){
        return "login/login";
    }

    @PostMapping("/principal")
    public String principal(){
        return "redirect:/account/principal";
    }


    
}
