package com.etnetera.hr.controller;

import com.etnetera.hr.converter.JavaScriptFrameworkConverter;
import com.etnetera.hr.dto.JavaScriptFrameworkDTO;
import com.etnetera.hr.service.JavaScriptFrameworkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.etnetera.hr.data.JavaScriptFramework;
import com.etnetera.hr.repository.JavaScriptFrameworkRepository;

/**
 * Simple REST controller for accessing application logic.
 * 
 * @author Etnetera
 *
 */
@RestController
public class JavaScriptFrameworkController {

	private final JavaScriptFrameworkRepository repository;
	private final JavaScriptFrameworkService service;

	@Autowired
	public JavaScriptFrameworkController(JavaScriptFrameworkRepository repository, JavaScriptFrameworkService service) {
		this.repository = repository;
		this.service = service;
	}

	@GetMapping("/frameworks")
	public Iterable<JavaScriptFramework> frameworks() {
		return repository.findAll();
	}

	@PostMapping("/frameworks")
	public JavaScriptFrameworkDTO create(@RequestBody JavaScriptFrameworkDTO javaScriptFrameworkDTO){
		return JavaScriptFrameworkConverter.fromModel(service.create(JavaScriptFrameworkConverter.toModel(javaScriptFrameworkDTO)));
	}

}
