package com.etnetera.hr.service;

import com.etnetera.hr.data.JavaScriptFramework;
import com.etnetera.hr.exception.EntityStateException;
import com.etnetera.hr.repository.JavaScriptFrameworkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import java.util.Set;

@Service
public class JavaScriptFrameworkService {

    private final JavaScriptFrameworkRepository javaScriptFrameworkRepository;

    @Autowired
    public JavaScriptFrameworkService(JavaScriptFrameworkRepository javaScriptFrameworkRepository) {
        this.javaScriptFrameworkRepository = javaScriptFrameworkRepository;
    }

    /**
     * Method to find out if the framework with these properties exists
     * @param javaScriptFramework
     * @return true if yes, false if not
     */
    private boolean exists(JavaScriptFramework javaScriptFramework){
        Optional<JavaScriptFramework> optionalJavaScriptFramework = javaScriptFrameworkRepository.findByNameAndDeprecationDateAndHypeLevel(javaScriptFramework.getName(),javaScriptFramework.getDeprecationDate(), javaScriptFramework.getHypeLevel());
        return optionalJavaScriptFramework.isPresent();
    }

    /**
     * Method to find out if the framework with this id exists
     * @param id
     * @return true if yes, false if not
     */
    private boolean existsById(Long id){
        Optional<JavaScriptFramework> optionalJavaScriptFramework = javaScriptFrameworkRepository.findById(id);
        return optionalJavaScriptFramework.isPresent();
    }

    /**
     * Method for creating Framework, checking if the framework with these properties already exist
     * @param javaScriptFramework
     * @return JavaScriptFramework
     */
    @Transactional
    public JavaScriptFramework create(JavaScriptFramework javaScriptFramework) {
        if(exists(javaScriptFramework)){
            throw new EntityStateException(javaScriptFramework);
        }
        return javaScriptFrameworkRepository.save(javaScriptFramework);
    }

    /**
     * Method for deleting framework based on id, checking if the framework with this id exists
     * @param id
     */
    public void delete(Long id) {
        if(!existsById(id)){
            throw new EntityNotFoundException("The entity with this id does not exist");
        }
        javaScriptFrameworkRepository.deleteById(id);
    }

    /**
     * Method for updating Framework properties, checking if the framework with this ID exists
     *
     * @param id
     * @param javaScriptFramework
     * @return updatedJavaScriptFramework
     */
    @Transactional
    public JavaScriptFramework update(Long id,JavaScriptFramework javaScriptFramework) {
        if(!existsById(id)){
           throw new EntityNotFoundException("The entity with this id does not exist");
        }
        Optional<JavaScriptFramework> optionalUpdatedJavaScriptFramework = javaScriptFrameworkRepository.findById(id);
        JavaScriptFramework updatedJavaScriptFramework = optionalUpdatedJavaScriptFramework.get();
        updatedJavaScriptFramework.setName(javaScriptFramework.getName());
        updatedJavaScriptFramework.setDeprecationDate(javaScriptFramework.getDeprecationDate());
        updatedJavaScriptFramework.setVersion(javaScriptFramework.getVersion());
        updatedJavaScriptFramework.setHypeLevel(javaScriptFramework.getHypeLevel());
        return updatedJavaScriptFramework;
    }

    /**
     * Method for returning Framework with requested id
     * @param id
     * @return JavaScriptFramework
     */
    public JavaScriptFramework readById(Long id) {
        if(!existsById(id)){
            throw new EntityNotFoundException();
        }
        return javaScriptFrameworkRepository.findById(id).get();
    }

    /**
     * Method for reading Frameworks by name, if none of the Framework has the requested than method throw NotFoundException
     * @param name
     * @return Collection<JavaScriptFramework>
     */
    public Collection<JavaScriptFramework> readByName(String name) {
        ArrayList<JavaScriptFramework> javaScriptFrameworks = (ArrayList<JavaScriptFramework>) javaScriptFrameworkRepository.findByName(name);
        if(javaScriptFrameworks.isEmpty()){
            throw new EntityNotFoundException("No framework with this name");
        }
        return javaScriptFrameworks;
    }

    /**
     * Method for reading Frameworks by date, if none of the Framework has the requested than method throw NotFoundException
     * @param date
     * @return Collection<JavaScriptFramework>
     */
    public Collection<JavaScriptFramework> readByDate(LocalDate date) {
         ArrayList<JavaScriptFramework> javaScriptFrameworks = (ArrayList<JavaScriptFramework>) javaScriptFrameworkRepository.findByDeprecationDate(date);
         if(javaScriptFrameworks.isEmpty()){
             throw  new EntityNotFoundException("No framework with this date");
         }
         return javaScriptFrameworks;
    }

    /**
     * Method for reading Frameworks by hype, if none of the Framework has the requested than method throw NotFoundException
     * @param hype
     * @return Collection<JavaScriptFramework>
     */
    public Collection<JavaScriptFramework> readByHype(double hype) {
        ArrayList<JavaScriptFramework> javaScriptFrameworks = (ArrayList<JavaScriptFramework>) javaScriptFrameworkRepository.findByHypeLevel(hype);
        if(javaScriptFrameworks.isEmpty()){
            throw  new EntityNotFoundException("No framework with this hype level");
        }
        return javaScriptFrameworks;
    }

    /**
     * Method for reading Frameworks by version, if none of the Framework has the requested than method throw NotFoundException
     * @param version
     * @return Collection<JavaScriptFramework>
     */
    public Collection<JavaScriptFramework> readByVersion(String version) {
        ArrayList<JavaScriptFramework> javaScriptFrameworks = (ArrayList<JavaScriptFramework>) javaScriptFrameworkRepository.findByVersion(version);
        if(javaScriptFrameworks.isEmpty()){
            throw  new EntityNotFoundException("No framework with this version");
        }
        return javaScriptFrameworks;
    }

    public Collection<JavaScriptFramework> readAll(){
        return (Collection<JavaScriptFramework>) javaScriptFrameworkRepository.findAll();
    }
}
