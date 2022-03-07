package com.etnetera.hr.converter;

import com.etnetera.hr.data.JavaScriptFramework;
import com.etnetera.hr.dto.JavaScriptFrameworkDTO;

public class JavaScriptFrameworkConverter {

    public static JavaScriptFramework toModel(JavaScriptFrameworkDTO javaScriptFrameworkDTO){
        return new JavaScriptFramework();
    }

    public static JavaScriptFrameworkDTO fromModel(JavaScriptFramework javaScriptFramework){
        return new JavaScriptFrameworkDTO();
    }

}
