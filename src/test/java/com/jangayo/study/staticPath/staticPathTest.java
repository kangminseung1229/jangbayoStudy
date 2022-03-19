package com.jangayo.study.staticPath;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class staticPathTest {


    @Autowired
    MockMvc mockMvc;

    @DisplayName("bootstrap 경로 정상")
    @Test
    void resourcesPathTest_bootstrap() throws Exception{

        mockMvc.perform(get("/node_modules/bootstrap/dist/js/bootstrap.bundle.js")
                        )
                        .andExpect(status().isOk());
    }
    @DisplayName("jquery 경로 정상")
    @Test
    void resourcesPathTest_jquery() throws Exception{

        mockMvc.perform(get("/node_modules/jquery/dist/jquery.min.js")
                        )
                        .andExpect(status().isOk());
    }

    
}
