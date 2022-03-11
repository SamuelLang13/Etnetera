package com.etnetera.hr;

import com.etnetera.hr.data.JavaScriptFramework;
import com.etnetera.hr.exception.EntityStateException;
import com.etnetera.hr.repository.JavaScriptFrameworkRepository;
import com.etnetera.hr.service.JavaScriptFrameworkService;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityNotFoundException;

import static org.mockito.AdditionalMatchers.not;
import static org.mockito.ArgumentMatchers.*;

import java.time.LocalDate;
import java.util.*;

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

        Mockito.lenient().when(repository.findById(1L)).thenReturn(Optional.of(javaScriptFramework1));
        Mockito.lenient().when(repository.findById(not(eq(1L)))).thenReturn(Optional.empty());

        Assertions.assertTrue(service.existsById(1L));
        Assertions.assertFalse(service.existsById(2L));
    }

    @Test
    public void testCreate(){
        JavaScriptFramework javaScriptFramework1 = new JavaScriptFramework(1L,"JS1", List.of("13.4"), LocalDate.of(2022,2,4),8.7);
        JavaScriptFramework javaScriptFramework2 = new JavaScriptFramework(2L,"JS2", List.of("23.4"), LocalDate.of(2019,4,7),5.6);

        Mockito.lenient().when(repository.findByNameAndDeprecationDateAndHypeLevel("JS1",LocalDate.of(2022,2,4),8.7)).thenReturn(Optional.of(javaScriptFramework1));
        Mockito.lenient().when(repository.findByNameAndDeprecationDateAndHypeLevel(not(eq("JS1")),not(eq(LocalDate.of(2022,2,4))),not(eq(8.7)))).thenReturn(Optional.empty());

        Assertions.assertThrows(EntityStateException.class,()->service.create(javaScriptFramework1));
        Assertions.assertEquals(repository.save(javaScriptFramework2),service.create(javaScriptFramework2));
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

    @Test
    public void testDelete(){
        JavaScriptFramework javaScriptFramework1 = new JavaScriptFramework(1L,"JS1", List.of("13.4"), LocalDate.of(2022,2,4),8.7);

        Mockito.lenient().when(repository.findById(1L)).thenReturn(Optional.of(javaScriptFramework1));
        Mockito.lenient().when(repository.findById(not(eq(1L)))).thenReturn(Optional.empty());

        Assertions.assertThrows(EntityNotFoundException.class,()->service.delete(2L));
    }

    @Test
    public void testReadByName(){
        JavaScriptFramework javaScriptFramework1 = new JavaScriptFramework("JS1", List.of("13.4"), LocalDate.of(2022,2,4),8.7);
        JavaScriptFramework javaScriptFramework2 = new JavaScriptFramework("JS1", List.of("23.4"), LocalDate.of(2019,4,7),5.6);

        ArrayList<JavaScriptFramework> javaScriptFrameworks = new ArrayList<>();
        javaScriptFrameworks.add(javaScriptFramework1);
        javaScriptFrameworks.add(javaScriptFramework2);

        Mockito.lenient().when(repository.findByName("JS1")).thenReturn(javaScriptFrameworks);
        Mockito.lenient().when(repository.findByName(not(eq("JS1")))).thenThrow(new EntityNotFoundException());

        Assertions.assertThrows(EntityNotFoundException.class,()->service.readByName("JS2"));
        Assertions.assertEquals(javaScriptFrameworks,service.readByName("JS1"));
    }

    @Test
    public void testReadByDate(){
        JavaScriptFramework javaScriptFramework1 = new JavaScriptFramework("JS1", List.of("13.4"), LocalDate.of(2022,2,4),8.7);
        JavaScriptFramework javaScriptFramework2 = new JavaScriptFramework("JS1", List.of("23.4"), LocalDate.of(2022,2,4),5.6);

        ArrayList<JavaScriptFramework> javaScriptFrameworks = new ArrayList<>();
        javaScriptFrameworks.add(javaScriptFramework1);
        javaScriptFrameworks.add(javaScriptFramework2);

        Mockito.lenient().when(repository.findByDeprecationDate(LocalDate.of(2022,2,4))).thenReturn(javaScriptFrameworks);
        Mockito.lenient().when(repository.findByDeprecationDate(not(eq(LocalDate.of(2022,2,4))))).thenThrow(new EntityNotFoundException());

        Assertions.assertThrows(EntityNotFoundException.class,()->service.readByDate(LocalDate.of(2019,2,5)));
        Assertions.assertEquals(javaScriptFrameworks,service.readByDate(LocalDate.of(2022,2,4)));
    }

    @Test
    public void testReadByHype(){
        JavaScriptFramework javaScriptFramework1 = new JavaScriptFramework("JS1", List.of("13.4"), LocalDate.of(2022,2,4),8.7);
        JavaScriptFramework javaScriptFramework2 = new JavaScriptFramework("JS1", List.of("23.4"), LocalDate.of(2019,4,1),8.7);

        ArrayList<JavaScriptFramework> javaScriptFrameworks = new ArrayList<>();
        javaScriptFrameworks.add(javaScriptFramework1);
        javaScriptFrameworks.add(javaScriptFramework2);

        Mockito.lenient().when(repository.findByHypeLevel(8.7)).thenReturn(javaScriptFrameworks);
        Mockito.lenient().when(repository.findByHypeLevel(not(eq(8.7)))).thenThrow(new EntityNotFoundException());

        Assertions.assertThrows(EntityNotFoundException.class,()->service.readByHype(8.6));
        Assertions.assertEquals(javaScriptFrameworks,service.readByHype(8.7));
    }

    @Test
    public void testReadByVersion(){
        JavaScriptFramework javaScriptFramework1 = new JavaScriptFramework("JS1", List.of("13.4"), LocalDate.of(2022,2,4),8.7);
        JavaScriptFramework javaScriptFramework2 = new JavaScriptFramework("JS2", List.of("13.4"), LocalDate.of(2019,4,1),5.7);

        ArrayList<JavaScriptFramework> javaScriptFrameworks = new ArrayList<>();
        javaScriptFrameworks.add(javaScriptFramework1);
        javaScriptFrameworks.add(javaScriptFramework2);

        Mockito.lenient().when(repository.findByVersion("13.4")).thenReturn(javaScriptFrameworks);
        Mockito.lenient().when(repository.findByVersion(not(eq("13.4")))).thenThrow(new EntityNotFoundException());

        Assertions.assertThrows(EntityNotFoundException.class,()->service.readByVersion("2"));
        Assertions.assertEquals(javaScriptFrameworks,service.readByVersion("13.4"));
    }

    @Test
    public void testReadAll(){
        JavaScriptFramework javaScriptFramework1 = new JavaScriptFramework("JS1", List.of("13.4"), LocalDate.of(2022,2,4),8.7);
        JavaScriptFramework javaScriptFramework2 = new JavaScriptFramework("JS2", List.of("13.4"), LocalDate.of(2019,4,1),5.7);

        ArrayList<JavaScriptFramework> javaScriptFrameworks = new ArrayList<>();
        javaScriptFrameworks.add(javaScriptFramework1);
        javaScriptFrameworks.add(javaScriptFramework2);

        Mockito.lenient().when(repository.findAll()).thenReturn(javaScriptFrameworks);
        Assertions.assertEquals(javaScriptFrameworks,service.readAll());
    }
}
