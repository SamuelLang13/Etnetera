package com.etnetera.hr.repository;

import org.springframework.data.repository.CrudRepository;

import com.etnetera.hr.data.JavaScriptFramework;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.*;

/**
 * Spring data repository interface used for accessing the data in database.
 * 
 * @author Etnetera
 *
 */
@Repository
public interface JavaScriptFrameworkRepository extends CrudRepository<JavaScriptFramework, Long> {

    public Optional<JavaScriptFramework> findByNameAndDeprecationDateAndHypeLevel(String Name,LocalDate deprecationDate, Double hypeLevel);

    Collection<JavaScriptFramework> findByName(String name);

    Collection<JavaScriptFramework> findByDeprecationDate(LocalDate date);

    Collection<JavaScriptFramework> findByHypeLevel(double hype);
}
