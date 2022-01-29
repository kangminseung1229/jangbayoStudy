package com.jangayo.study.board;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@AutoConfigureMockMvc
@Slf4j
public class CousultJPAtest {

    @Autowired
    private ConsultRepository consultRepository;

    @DisplayName("생성일 자동 생성 테스트")
    @Test
    void createTime() throws Exception {

        Consult consult = Consult.builder()
                .userid("kang")
                .consultTitle("help me")    
                .consultText("please call me")
                .build();

        Consult newConsult = consultRepository.save(consult);

        log.info("log :: " + newConsult);

    }

    @DisplayName("수정일 자동 수정 테스트")
    @Test
    void updateTime() throws Exception {

        Consult consult = new Consult();

        consult.setId(1l);
        consult.setUserid("kang");
        consult.setConsultTitle("title");
        consult.setConsultText("Text");

        Consult newConsult = consultRepository.save(consult);

        log.info("log :: " + newConsult);

    }

}
