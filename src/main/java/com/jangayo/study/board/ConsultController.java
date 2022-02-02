package com.jangayo.study.board;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.jsf.FacesContextUtils;

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

    @GetMapping("consult-detail")
    public String detail(Model model, @RequestParam(required = false) Long id){

        if (id != null) {

            Optional<Consult> consult = consultRepository.findById(id);

            if (consult.isPresent()) {
                model.addAttribute("consult", consult.get());
            }
        } else { 
            model.addAttribute("consult", new Consult());
        }


        model.addAttribute("title", "상담글");
        return "thymeleaf/consultDetail";
    }

    @PostMapping("consult-detail")
    public String detailPost(Consult consult, Model model){

        System.out.println("detail ::" + consult);
        consult.setCreated(consultRepository.findById(consult.getId()).get().getCreated());
        Consult updatedConsult = consultRepository.save(consult);

        model.addAttribute("consult", updatedConsult);

        return "redirect:/consult/consult-detail";

    }

}
