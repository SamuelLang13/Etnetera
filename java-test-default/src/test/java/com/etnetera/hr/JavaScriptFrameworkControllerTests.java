package com.etnetera.hr;

import com.etnetera.hr.controller.JavaScriptFrameworkController;
import com.etnetera.hr.data.JavaScriptFramework;
import com.etnetera.hr.exception.EntityStateException;
import com.etnetera.hr.service.JavaScriptFrameworkService;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.stubbing.OngoingStubbing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.AdditionalMatchers.not;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class JavaScriptFrameworkControllerTests {

    @MockBean
    JavaScriptFrameworkService service;

    @Autowired
    MockMvc mockMvc;

    @Test
    public void testGetAll() throws Exception {
        JavaScriptFramework javaScriptFramework1 = new JavaScriptFramework("JS1", List.of("13.4"), LocalDate.of(2022,02,04),8.7);
        JavaScriptFramework javaScriptFramework2 = new JavaScriptFramework("JS2", List.of("23.4"), LocalDate.of(2019,04,07),5.6);

        List<JavaScriptFramework> javaScriptFrameworks = List.of(javaScriptFramework1,javaScriptFramework2);

        Mockito.when(service.readAll()).thenReturn(javaScriptFrameworks);

        mockMvc.perform(get("/frameworks"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(2)));

    }

    @Test
    public void testGetOne() throws Exception {

        JavaScriptFramework javaScriptFramework = new JavaScriptFramework(1L,"JS1", List.of("13.4"), LocalDate.of(2022,2,4),8.7);
        Mockito.when(service.readById(javaScriptFramework.getId())).thenReturn(javaScriptFramework);
        mockMvc.perform(get("/frameworks/getById/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name",Matchers.is("JS1")));
        Mockito.when(service.readById(not(eq(javaScriptFramework.getId())))).thenThrow(new EntityNotFoundException());
        mockMvc.perform(get("/frameworks/getById/2"))
                .andExpect(status().isNotFound());

    }

    @Test
    public void testDelete() throws Exception{
        JavaScriptFramework javaScriptFramework = new JavaScriptFramework(1L,"JS1", List.of("13.4"), LocalDate.of(2022,2,4),8.7);
        Mockito.when(service.readById(javaScriptFramework.getId())).thenReturn(javaScriptFramework);
        Mockito.when(service.readById(not(eq(javaScriptFramework.getId())))).thenThrow(new EntityNotFoundException());

        mockMvc.perform(get("/frameworks/getById/-1"))
                .andExpect(status().isNotFound());

        //Checking to never use delete method if the id does not exist
        verify(service,never()).delete(any());

        mockMvc.perform(delete("/frameworks/1"))
                .andExpect(status().isOk());

        //Checking if the correct JSF was deleted
        verify(service,times(1)).delete(javaScriptFramework.getId());
    }

    @Test
    public void testCreateExisting() throws Exception {

        doThrow(new EntityStateException()).when(service).create(any(JavaScriptFramework.class));

        mockMvc.perform(post("/frameworks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"JavaScriptFramework1\"," +
                                "\"version\":[\"1.3\",\"3.3\"]," +
                                "\"deprecationDate\":\"2022-03-08\"," +
                                "\"hypeLevel\":\"43.4\"}"))
                .andExpect(status().isConflict());
    }

    @Test
    public void testCreate() throws Exception{
        JavaScriptFramework javaScriptFramework = new JavaScriptFramework(1L,"JS1", List.of("13.4"), LocalDate.of(2022,2,4),8.7);
        Mockito.when(service.readById(javaScriptFramework.getId())).thenReturn(javaScriptFramework);
        Mockito.when(service.readById(not(eq(javaScriptFramework.getId())))).thenThrow(new EntityNotFoundException());

        mockMvc.perform(post("/frameworks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"JS1\"," +
                                "\"version\":[\"13.4\"]," +
                                "\"deprecationDate\":\"2022-02-04\"," +
                                "\"hypeLevel\":\"8.7\"}"))
                .andExpect(status().isOk());

        ArgumentCaptor<JavaScriptFramework> argumentCaptor = ArgumentCaptor.forClass(JavaScriptFramework.class);
        Mockito.verify(service, Mockito.times(1)).create(argumentCaptor.capture());
        JavaScriptFramework javaScriptFramework1 = argumentCaptor.getValue();
        assertEquals("JS1",javaScriptFramework1.getName());

    }


}
