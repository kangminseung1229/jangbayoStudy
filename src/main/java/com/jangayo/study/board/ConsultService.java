package com.jangayo.study.board;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ConsultService {

    private final ConsultRepository consultRepository;

    public void detailProcess(Model model, Long id) {
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
    }

    public Consult updateProcess(ConsultForm consultForm) {
        Consult newConsult;
        if (consultForm.getId() == null) {
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
        return newConsult;
    }

    public void answerProcess(Model model, Optional<Consult> consult) {
        Consult detailConsult = consult.get();

        ConsultAnswerForm consultAnswerForm = ConsultAnswerForm.builder()
                .id(detailConsult.getId())
                .answerTitle(detailConsult.getAnswerTitle())
                .answerText(detailConsult.getAnswerText())
                .build();
        model.addAttribute("consult", detailConsult);
        model.addAttribute("consultAnswerForm", consultAnswerForm);
    }

    public void updateProcess(ConsultAnswerForm consultAnswerForm, Model model, Consult consult) {
        Consult newConsult = consult;

        newConsult.setAnswerTitle(consultAnswerForm.getAnswerTitle());
        newConsult.setAnswerText(consultAnswerForm.getAnswerText());
        newConsult.setAnswerTime(LocalDateTime.now());

        newConsult = consultRepository.save(newConsult);

        model.addAttribute("consult", newConsult);
        model.addAttribute("consultAnswerForm", consultAnswerForm);
    }

}
