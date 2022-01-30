package com.jangayo.study.board;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@AutoConfigureMockMvc
@Slf4j
public class CousultJPAtest {

    @Autowired
    private ConsultRepository consultRepository;

    @DisplayName("create Time test")
    @Test
    void createTime() throws Exception {

        Consult consult = Consult.builder()
                .userid("new")
                .consultTitle("newConsult5")
                .consultText("newConsultText")
                .build();

        Consult newConsult = consultRepository.save(consult);
    }

    @DisplayName("update Time test")
    @Test
    @Transactional
    @Rollback(false)
    void updateTimetest() throws Exception{
        
        Optional<Consult> consult = consultRepository.findById(5l);
        
        Consult newConsult = Consult.builder()
                .id(consult.get().getId())
                .userid(consult.get().getUserid())
                .consultTitle(consult.get().getConsultTitle())
                .created(consult.get().getCreated())
                .answerTitle("답변입니다.")
                .answerText("답변내용")
                .build();

        consultRepository.save(newConsult);
        
    }


    @DisplayName("다이나믹 업데이트")
    @Test
    @Transactional
    @Rollback(false)
    void DynamicUpdate() throws Exception {

        Consult newConsult = Consult.builder()
                .id(3l)
                .userid("kkk")
                .consultTitle("다이나믹안쓰는제목")
                .answerTitle("답변제목입니다2.")
                .answerText("답변텍스트입니다2")
                .build();

        consultRepository.save(newConsult);


    }

}
