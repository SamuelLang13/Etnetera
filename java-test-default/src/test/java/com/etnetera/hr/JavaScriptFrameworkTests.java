package com.etnetera.hr;

import com.etnetera.hr.controller.JavaScriptFrameworkController;
import com.etnetera.hr.data.JavaScriptFramework;
import com.etnetera.hr.exception.EntityStateException;
import com.etnetera.hr.service.JavaScriptFrameworkService;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.matches;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class JavaScriptFrameworkTests {

    @MockBean
    JavaScriptFrameworkService service;

    @MockBean
    JavaScriptFrameworkController controller;

    @Autowired
    MockMvc mockMvc;

    @Test
    public void testGetAll() throws Exception {
        JavaScriptFramework javaScriptFramework1 = new JavaScriptFramework("JS1", List.of("13.4"), LocalDate.of(2022,02,04),8.7);
        JavaScriptFramework javaScriptFramework2 = new JavaScriptFramework("JS2", List.of("23.4"), LocalDate.of(2019,04,07),5.6);

        List<JavaScriptFramework> javaScriptFrameworks = List.of(javaScriptFramework1,javaScriptFramework2);

        Mockito.when(controller.frameworks()).thenReturn(javaScriptFrameworks);

        mockMvc.perform(get("/frameworks"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(2)));

    }

//    @Test
//    public void testCreateExisting() throws Exception {
//        doThrow(new EntityStateException()).when(service).create(any(JavaScriptFramework.class));
//
//        mockMvc.perform(post("/frameworks")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content("{\"name\":\"JavaScriptFramework1\"," +
//                                "\"version\":[\"1.3\",\"3.3\"]," +
//                                "\"deprecationDate\":\"2022-03-08\"," +
//                                "\"hypeLevel\":\"43.4\"}"))
//                .andExpect(status().isConflict());
//    }

}
