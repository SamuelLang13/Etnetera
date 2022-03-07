package com.etnetera.hr;

import com.etnetera.hr.controller.JavaScriptFrameworkController;
import com.etnetera.hr.data.JavaScriptFramework;
import com.etnetera.hr.exception.EntityStateException;
import com.etnetera.hr.service.JavaScriptFrameworkService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Class used for Spring Boot/MVC based tests.
 * 
 * @author Etnetera
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@WebMvcTest(JavaScriptFrameworkController.class)
public class JavaScriptFrameworkTests {

    @MockBean
    JavaScriptFrameworkService service;

    @MockBean
    MockMvc mockMvc;

    @Test
    public void testCreateExists() throws Exception {
        doThrow(new EntityStateException()).when(service).create(any(JavaScriptFramework.class));

        mockMvc.perform(post("/frameworks")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"JavaScriptFramework\"," +
                        "\"version\":\"23.4.5\"," +
                        "\"deprecationDate\":\"2022-03-08\"," +
                        "\"hypeLevel\":\"43.4\"}"))
                .andExpect(status().isConflict());
    }

}
