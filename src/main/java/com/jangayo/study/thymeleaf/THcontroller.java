package com.jangayo.study.thymeleaf;

import com.fasterxml.jackson.annotation.JsonCreator.Mode;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class THcontroller {

    @GetMapping("page1")
    public String page1(Model model) {

        model.addAttribute("form", new form());

        return "thymeleaf/page1";
    }

    @PostMapping("page2")
    public String page2(form form, Model model) {

        model.addAttribute("form", form);

        return "thymeleaf/page2";
    }

}
