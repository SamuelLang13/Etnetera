package com.etnetera.hr.controller;

import com.etnetera.hr.converter.JavaScriptFrameworkConverter;
import com.etnetera.hr.dto.JavaScriptFrameworkDTO;
import com.etnetera.hr.service.JavaScriptFrameworkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import com.etnetera.hr.data.JavaScriptFramework;
import com.etnetera.hr.repository.JavaScriptFrameworkRepository;

import java.time.LocalDate;
import java.util.Collection;

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
	@DeleteMapping("/frameworks/{id}")
	public void delete(@PathVariable Long id){
		service.delete(id);
	}

	@PutMapping("/frameworks/{id}")
	public JavaScriptFrameworkDTO update(@PathVariable Long id, @RequestBody JavaScriptFrameworkDTO javaScriptFrameworkDTO){
		return JavaScriptFrameworkConverter.fromModel(service.update(id,JavaScriptFrameworkConverter.toModel(javaScriptFrameworkDTO)));
	}

	@GetMapping("/frameworks/getById/{id}")
	public JavaScriptFrameworkDTO getOne(@PathVariable Long id){
		return JavaScriptFrameworkConverter.fromModel(service.readById(id));
	}
	@GetMapping("/frameworks/getByName/{name}")
	public Collection<JavaScriptFrameworkDTO> getByName(@PathVariable String name){
		return JavaScriptFrameworkConverter.fromModels(service.readByName(name));
	}

	@GetMapping("/frameworks/getByDate/{date}")
	public Collection<JavaScriptFrameworkDTO> getByDate(@PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date){
		return JavaScriptFrameworkConverter.fromModels(service.readByDate(date));
	}

	@GetMapping("/frameworks/getByHype/{hype}")
	public Collection<JavaScriptFrameworkDTO> getByHype(@PathVariable double hype){
		return JavaScriptFrameworkConverter.fromModels(service.readByHype(hype));
	}

	@GetMapping("/frameworks/getByVersion/{version}")
	public Collection<JavaScriptFrameworkDTO> getByVersion(@PathVariable String version){
		return JavaScriptFrameworkConverter.fromModels(service.readByVersion(version));
	}

}
