package com.etnetera.hr.repository;

import org.springframework.data.repository.CrudRepository;

import com.etnetera.hr.data.JavaScriptFramework;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

/**
 * Spring data repository interface used for accessing the data in database.
 * 
 * @author Etnetera
 *
 */
@Repository
public interface JavaScriptFrameworkRepository extends CrudRepository<JavaScriptFramework, Long> {

    public Optional<JavaScriptFramework> findByNameAndVersionAndDeprecationDateAndHypeLevel(String Name, String Version,
                                                                                            LocalDate deprecationDate, Double hypeLevel);

}
