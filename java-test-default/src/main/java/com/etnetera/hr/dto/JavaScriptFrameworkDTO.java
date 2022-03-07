package com.etnetera.hr.dto;

import javax.persistence.Column;
import java.time.LocalDate;
import java.util.Set;

public class JavaScriptFrameworkDTO {

    private Long id;
    private String name;
    private Set<String> version;
    private LocalDate deprecationDate;
    private Double hypeLevel;

    public JavaScriptFrameworkDTO(Long id, String name, Set<String> version, LocalDate deprecationDate, Double hypeLevel) {
        this.id = id;
        this.name = name;
        this.version = version;
        this.deprecationDate = deprecationDate;
        this.hypeLevel = hypeLevel;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<String> getVersion() {
        return version;
    }

    public void setVersion(Set<String> version) {
        this.version = version;
    }

    public LocalDate getDeprecationDate() {
        return deprecationDate;
    }

    public void setDeprecationDate(LocalDate deprecationDate) {
        this.deprecationDate = deprecationDate;
    }

    public Double getHypeLevel() {
        return hypeLevel;
    }

    public void setHypeLevel(Double hypeLevel) {
        this.hypeLevel = hypeLevel;
    }
}
