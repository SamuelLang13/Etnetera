package com.etnetera.hr.converter;

import com.etnetera.hr.data.JavaScriptFramework;
import com.etnetera.hr.dto.JavaScriptFrameworkDTO;

import java.util.Collection;
import java.util.stream.Collectors;

public class JavaScriptFrameworkConverter {

    /**
     * Converting DTO to entity model
     * @param javaScriptFrameworkDTO
     * @return JavaScriptFramework
     */
    public static JavaScriptFramework toModel(JavaScriptFrameworkDTO javaScriptFrameworkDTO){
        return new JavaScriptFramework(javaScriptFrameworkDTO.getName(), javaScriptFrameworkDTO.getVersion(), javaScriptFrameworkDTO.getDeprecationDate(), javaScriptFrameworkDTO.getHypeLevel());
    }

    /**
     * Converting entity model to DTO
     * @param javaScriptFramework
     * @return JavaScriptFrameworkDTO
     */
    public static JavaScriptFrameworkDTO fromModel(JavaScriptFramework javaScriptFramework){
        return new JavaScriptFrameworkDTO(javaScriptFramework.getId(), javaScriptFramework.getName(), javaScriptFramework.getVersion(), javaScriptFramework.getDeprecationDate(), javaScriptFramework.getHypeLevel());
    }

    public static Collection<JavaScriptFrameworkDTO> fromModels(Collection<JavaScriptFramework> javaScriptFrameworks){
        return javaScriptFrameworks.stream().map(JavaScriptFrameworkConverter::fromModel).collect(Collectors.toList());
    }

}
