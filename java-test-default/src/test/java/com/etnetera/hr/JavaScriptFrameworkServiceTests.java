package com.etnetera.hr;

import com.etnetera.hr.controller.JavaScriptFrameworkController;
import com.etnetera.hr.data.JavaScriptFramework;
import com.etnetera.hr.exception.EntityStateException;
import com.etnetera.hr.repository.JavaScriptFrameworkRepository;
import com.etnetera.hr.service.JavaScriptFrameworkService;
import org.hibernate.service.spi.InjectService;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import javax.persistence.EntityNotFoundException;

import static org.mockito.AdditionalMatchers.not;
import static org.mockito.ArgumentMatchers.*;

import java.time.LocalDate;
import java.util.*;
import java.util.function.BooleanSupplier;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class JavaScriptFrameworkServiceTests {

    @Mock
    JavaScriptFrameworkRepository repository;

    @InjectMocks
    JavaScriptFrameworkService service;

    @Test
    public void testExists(){
        JavaScriptFramework javaScriptFramework1 = new JavaScriptFramework("JS1", List.of("13.4"), LocalDate.of(2022,2,4),8.7);
        JavaScriptFramework javaScriptFramework2 = new JavaScriptFramework("JS2", List.of("23.4"), LocalDate.of(2019,4,7),5.6);
        
        Mockito.lenient().when(repository.findByNameAndDeprecationDateAndHypeLevel("JS1",LocalDate.of(2022,2,4),8.7)).thenReturn(Optional.of(javaScriptFramework1));
        Mockito.lenient().when(repository.findByNameAndDeprecationDateAndHypeLevel(not(eq("JS1")),not(eq(LocalDate.of(2022,2,4))),not(eq(8.7)))).thenReturn(Optional.empty());

        Assertions.assertTrue(service.exists(javaScriptFramework1));
        Assertions.assertFalse(service.exists(javaScriptFramework2));
    }

    @Test
    public void testExistsById(){
        JavaScriptFramework javaScriptFramework1 = new JavaScriptFramework(1L,"JS1", List.of("13.4"), LocalDate.of(2022,2,4),8.7);
        JavaScriptFramework javaScriptFramework2 = new JavaScriptFramework("JS2", List.of("23.4"), LocalDate.of(2019,4,7),5.6);

        Mockito.lenient().when(repository.findById(1L)).thenReturn(Optional.of(javaScriptFramework1));
        Mockito.lenient().when(repository.findById(not(eq(1L)))).thenReturn(Optional.empty());

        Assertions.assertTrue(service.existsById(1L));
        Assertions.assertFalse(service.existsById(2L));
    }

    @Test
    public void testCreate(){
        JavaScriptFramework javaScriptFramework1 = new JavaScriptFramework(1L,"JS1", List.of("13.4"), LocalDate.of(2022,2,4),8.7);
        JavaScriptFramework javaScriptFramework2 = new JavaScriptFramework(2L,"JS2", List.of("23.4"), LocalDate.of(2019,4,7),5.6);

        Mockito.lenient().when(service.exists(javaScriptFramework1)).thenThrow(new EntityStateException());

        Assertions.assertThrows(EntityStateException.class,()->service.create(javaScriptFramework1));
    }

    @Test
    public void testUpdate(){
        JavaScriptFramework javaScriptFramework1 = new JavaScriptFramework(1L,"JS1", List.of("13.4"), LocalDate.of(2022,2,4),8.7);
        JavaScriptFramework javaScriptFramework2 = new JavaScriptFramework(1L,"JS2", List.of("13.4"), LocalDate.of(2022,2,4),8.7);

        Mockito.lenient().when(repository.findById(1L)).thenReturn(Optional.of(javaScriptFramework1));
        Mockito.lenient().when(repository.findById(not(eq(1L)))).thenReturn(Optional.empty());

        Assertions.assertEquals(javaScriptFramework2,service.update(1L,javaScriptFramework2));
        Assertions.assertThrows(EntityNotFoundException.class,()->service.update(-1L,javaScriptFramework2));

    }

}
