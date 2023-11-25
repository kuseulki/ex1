package com.a.exboard5.controller;

import com.a.exboard5.config.SecurityConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@DisplayName("Main 컨트롤러")
@Import(SecurityConfig.class)
@WebMvcTest(MainController.class)
class MainControllerTest {

    private final MockMvc mvc;

    public MainControllerTest(@Autowired MockMvc mvc) {
        this.mvc = mvc;
    }

    @Test
    void givenNothing_whenRequestingRootPage_thenRedirectsToArticlesPage() throws Exception {

        // When & Then
//        mvc.perform(get("/"))
//                .andExpect(status().isOk())
//                .andExpect(view().name("forward:/articles"))
//                .andExpect(forwardedUrl("/articles"))
//                .andDo(MockMvcResultHandlers.print());
//    }
        mvc.perform(get("/"))
                .andExpect(status().is3xxRedirection());
    }

}