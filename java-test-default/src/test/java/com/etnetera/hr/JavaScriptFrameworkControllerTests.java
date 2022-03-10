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
        Mockito.when(service.create(javaScriptFramework)).thenReturn(javaScriptFramework);
        Mockito.when(service.create(not(eq(javaScriptFramework)))).thenReturn(new JavaScriptFramework(2L, "JS2", List.of("15.5"),
                LocalDate.of(2021,5,6), 48.5));

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

    @Test
    public void testUpdateNotExisting() throws Exception{
        JavaScriptFramework javaScriptFramework = new JavaScriptFramework(1L,"JS1", List.of("13.4"), LocalDate.of(2022,2,4),8.7);
        Mockito.when(service.update(javaScriptFramework.getId(),javaScriptFramework)).thenReturn(javaScriptFramework);
        Mockito.when(service.update(not(eq(javaScriptFramework.getId())),any())).thenThrow(new EntityNotFoundException());

        mockMvc.perform(put("/frameworks/-1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"JS3\"," +
                        "\"version\":[\"19.4\"]," +
                        "\"deprecationDate\":\"2022-02-04\"," +
                        "\"hypeLevel\":\"8.7\"}"))
                .andExpect(status().isNotFound());

    }

    @Test
    public void testUpdate()throws Exception{
        JavaScriptFramework javaScriptFramework = new JavaScriptFramework(1L,"JS1", List.of("13.4"), LocalDate.of(2022,2,4),8.7);
        Mockito.when(service.update(javaScriptFramework.getId(),javaScriptFramework)).thenReturn(javaScriptFramework);
        Mockito.when(service.update((not(eq(javaScriptFramework.getId()))), any())).thenThrow(new EntityNotFoundException());

        mockMvc.perform(put("/frameworks/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"JS1\"," +
                                "\"version\":[\"13.4\"]," +
                                "\"deprecationDate\":\"2022-02-04\"," +
                                "\"hypeLevel\":\"8.7\"}"))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetByName() throws Exception{
        JavaScriptFramework javaScriptFramework1 = new JavaScriptFramework("JS1", List.of("13.4"), LocalDate.of(2022,2,4),8.7);
        JavaScriptFramework javaScriptFramework2 = new JavaScriptFramework("JS2", List.of("23.4"), LocalDate.of(2019,4,7),5.6);
        JavaScriptFramework javaScriptFramework3 = new JavaScriptFramework("JS1", List.of("13.4","45.4"), LocalDate.of(2019,8,1),8.7);

        List<JavaScriptFramework> javaScriptFrameworks = List.of(javaScriptFramework1,javaScriptFramework3);

        Mockito.when(service.readByName("JS1")).thenReturn(javaScriptFrameworks);

        mockMvc.perform(get("/frameworks/getByName/JS1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(2)))
                .andExpect(jsonPath("$[0].name",Matchers.is("JS1")))
                .andExpect(jsonPath("$[1].name",Matchers.is("JS1")));
    }


    @Test
    public void testGetByDate() throws Exception{
        JavaScriptFramework javaScriptFramework1 = new JavaScriptFramework("JS1", List.of("13.4"), LocalDate.of(2022,2,4),8.7);
        JavaScriptFramework javaScriptFramework2 = new JavaScriptFramework("JS2", List.of("23.4"), LocalDate.of(2019,4,7),5.6);
        JavaScriptFramework javaScriptFramework3 = new JavaScriptFramework("JS3", List.of("13.4","45.4"), LocalDate.of(2022,2,4),8.8);

        List<JavaScriptFramework> javaScriptFrameworks = List.of(javaScriptFramework1,javaScriptFramework3);

        Mockito.when(service.readByDate(LocalDate.of(2022,2,4))).thenReturn(javaScriptFrameworks);

        mockMvc.perform(get("/frameworks/getByDate/2022-02-04"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(2)))
                .andExpect(jsonPath("$[0].deprecationDate",Matchers.is("2022-02-04")))
                .andExpect(jsonPath("$[1].deprecationDate",Matchers.is("2022-02-04")));
    }

    @Test
    public void testGetByHype() throws Exception{
        JavaScriptFramework javaScriptFramework1 = new JavaScriptFramework("JS1", List.of("13.4"), LocalDate.of(2022,2,4),8.7);
        JavaScriptFramework javaScriptFramework2 = new JavaScriptFramework("JS2", List.of("23.4"), LocalDate.of(2019,4,7),5.6);
        JavaScriptFramework javaScriptFramework3 = new JavaScriptFramework("JS3", List.of("13.4","45.4"), LocalDate.of(2022,2,4),8.7);

        List<JavaScriptFramework> javaScriptFrameworks = List.of(javaScriptFramework1,javaScriptFramework3);

        Mockito.when(service.readByHype(8.7)).thenReturn(javaScriptFrameworks);

        mockMvc.perform(get("/frameworks/getByHype/8.7"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(2)))
                .andExpect(jsonPath("$[0].hypeLevel",Matchers.is(8.7)))
                .andExpect(jsonPath("$[1].hypeLevel",Matchers.is(8.7)));
    }


    @Test
    public void testGetByVersion() throws Exception{
        JavaScriptFramework javaScriptFramework1 = new JavaScriptFramework("JS1", List.of("13.4"), LocalDate.of(2022,2,4),8.7);
        JavaScriptFramework javaScriptFramework2 = new JavaScriptFramework("JS2", List.of("23.4"), LocalDate.of(2019,4,7),5.6);
        JavaScriptFramework javaScriptFramework3 = new JavaScriptFramework("JS3", List.of("13.4","45.4"), LocalDate.of(2022,2,4),8.7);

        List<JavaScriptFramework> javaScriptFrameworks = List.of(javaScriptFramework1,javaScriptFramework3);

        Mockito.when(service.readByVersion("13.4")).thenReturn(javaScriptFrameworks);

        mockMvc.perform(get("/frameworks/getByVersion/13.4"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(2)))
                .andExpect(jsonPath("$[0].version[0]",Matchers.is("13.4")))
                .andExpect(jsonPath("$[1].version[0]",Matchers.is("13.4")));
    }
}
