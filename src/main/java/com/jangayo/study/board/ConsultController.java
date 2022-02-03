package com.jangayo.study.board;

import java.util.Optional;

import javax.validation.Valid;

import org.aspectj.weaver.NewConstructorTypeMunger;
import org.springframework.boot.autoconfigure.info.ProjectInfoProperties.Build;
import org.springframework.data.domain.Page;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/consult")
@RequiredArgsConstructor
public class ConsultController {

    private final ConsultRepository consultRepository;
    private final ConsultFormValidator consultFormValidator;

    // 커스텀 validator
    @InitBinder("consultForm")
    public void InitBinder(WebDataBinder webDataBinder) {
        System.out.println("바인딩되었습니다.");
        webDataBinder.addValidators(consultFormValidator);
    }

    @GetMapping("consult-list")
    public String list(Model model, @PageableDefault(size = 20) org.springframework.data.domain.Pageable pageable,
            @RequestParam(required = false, defaultValue = "") String searchString) {

        Page<Consult> consultPagingList = consultRepository
                .findByUseridContainingOrConsultTitleContainingOrderByIdDesc(searchString, searchString, pageable);

        int startPage = Math.max(1,
                (consultPagingList.getPageable().getPageNumber() / pageable.getPageSize()) * pageable.getPageSize()
                        + 1);
        int endPage = Math.min(consultPagingList.getTotalPages(), startPage + pageable.getPageSize() - 1);

        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("list", consultPagingList);

        model.addAttribute("title", "상담리스트");

        return "thymeleaf/consultList";
    }

    @GetMapping("consult-detail")
    public String detail(Model model, @RequestParam(required = false) Long id) {

        if (id != null) {

            Optional<Consult> consult = consultRepository.findById(id);

            if (consult.isPresent()) {

                ConsultForm consultForm = ConsultForm.builder()
                        .userid(consult.get().getUserid())
                        .id(consult.get().getId())
                        .consultTitle(consult.get().getConsultTitle())
                        .consultText(consult.get().getConsultText())
                        .build();

                model.addAttribute("consultForm", consultForm);
            }
        } else {
            model.addAttribute("consultForm", new ConsultForm());
        }

        model.addAttribute("title", "상담글");
        return "thymeleaf/consultDetail";
    }

    // 상담글 작성 및 수정
    @PostMapping("consult-detail")
    public String detailPost(@Valid ConsultForm consultForm, Errors errors, Model model) {

        // valid 어노테이션으로 validation check를 진행한다.

        // error 가 존재한다면
        if (errors.hasErrors()) {
            return "thymeleaf/consultDetail";
        }

        

        Consult newConsult = new Consult();

        if (consultForm.getId().toString().isEmpty()) {
            newConsult = Consult.builder()
                    .id(null)
                    .userid(consultForm.getUserid())
                    .consultTitle(consultForm.getConsultTitle())
                    .consultText(consultForm.getConsultText())
                    .build();
        } else {
            newConsult = Consult.builder()
                    .id(consultForm.getId())
                    .userid(consultForm.getUserid())
                    .consultTitle(consultForm.getConsultTitle())
                    .consultText(consultForm.getConsultText())
                    .build();
        }

        consultRepository.save(newConsult);

        return "redirect:/consult/consult-detail?id=" + newConsult.getId();

    }

}
