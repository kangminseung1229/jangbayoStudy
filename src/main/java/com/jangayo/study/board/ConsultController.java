package com.jangayo.study.board;

import org.springframework.data.domain.Page;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/consult")
@RequiredArgsConstructor
public class ConsultController {

    private final ConsultRepository consultRepository;

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

}
