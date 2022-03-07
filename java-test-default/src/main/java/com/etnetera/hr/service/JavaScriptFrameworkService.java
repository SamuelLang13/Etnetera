package com.etnetera.hr.service;

import com.etnetera.hr.data.JavaScriptFramework;
import com.etnetera.hr.exception.EntityStateException;
import com.etnetera.hr.repository.JavaScriptFrameworkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class JavaScriptFrameworkService {

    private final JavaScriptFrameworkRepository javaScriptFrameworkRepository;

    @Autowired
    public JavaScriptFrameworkService(JavaScriptFrameworkRepository javaScriptFrameworkRepository) {
        this.javaScriptFrameworkRepository = javaScriptFrameworkRepository;
    }

    private boolean exists(JavaScriptFramework javaScriptFramework){
        Optional<JavaScriptFramework> optionalJavaScriptFramework = javaScriptFrameworkRepository.findByNameAndVersionAndDeprecationDateAndHypeLevel(javaScriptFramework.getName(), javaScriptFramework.getVersion(), javaScriptFramework.getDeprecationDate(), javaScriptFramework.getHypeLevel());
        return optionalJavaScriptFramework.isPresent();
    }

    @Transactional
    public JavaScriptFramework create(JavaScriptFramework javaScriptFramework) {
        if(exists(javaScriptFramework)){
            throw new EntityStateException(javaScriptFramework);
        }
        return javaScriptFrameworkRepository.save(javaScriptFramework);
    }
}
