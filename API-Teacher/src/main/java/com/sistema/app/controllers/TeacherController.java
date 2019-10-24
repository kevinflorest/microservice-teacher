package com.sistema.app.controllers;

import java.net.URI;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.support.WebExchangeBindException;
import com.sistema.app.models.documents.Teacher;
import com.sistema.app.models.service.TeacherService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequestMapping("/api/teacher")
@RestController
public class TeacherController {

	@Autowired
	private TeacherService service;
	
	@GetMapping
	public Mono<ResponseEntity<Flux<Teacher>>> findAll(){
		return Mono.just(
				ResponseEntity
				.ok()
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.body(service.findAllTeacher())
				);
	}
	
	@GetMapping("/{id}")
	public Mono<ResponseEntity<Teacher>> findTeacherById(@PathVariable String id){
		return service.findByIdTeacher(id).map(t ->
				ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON_UTF8).body(t)
				).defaultIfEmpty(ResponseEntity.notFound().build());
		
	}
	
	@GetMapping("names/{namesTeacher}")
	public Mono<ResponseEntity<Flux<Teacher>>> findByNamesTeacher(@PathVariable String namesTeacher){
		return Mono.just(
				ResponseEntity
				.ok()
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.body(service.findAllStudentByName(namesTeacher)));
	}
	
	@PostMapping
	public Mono<ResponseEntity<Map<String, Object>>> saveTeacher(@Valid @RequestBody Mono<Teacher> monoTeacher){
		
		Map<String, Object> response = new HashMap<String, Object>();
		
		return monoTeacher.flatMap(teacher -> {
			return service.saveTeacher(teacher).map(t-> {
				response.put("teacher", t);
				response.put("mensaje", "Profesor registrado con éxito");
				response.put("timestamp", new Date());
				return ResponseEntity
					.created(URI.create("/api/teacher/".concat(t.getId())))
					.contentType(MediaType.APPLICATION_JSON_UTF8)
					.body(response);
				});
			
		}).onErrorResume(r -> {
			return Mono.just(r).cast(WebExchangeBindException.class)
					.flatMap(e -> Mono.just(e.getFieldErrors()))
					.flatMapMany(Flux::fromIterable)
					.map(fieldError -> "El campo "+fieldError.getField() + " " + fieldError.getDefaultMessage())
					.collectList()
					.flatMap(list -> {
						response.put("errors", list);
						response.put("timestamp", new Date());
						response.put("status", HttpStatus.BAD_REQUEST.value());
						return Mono.just(ResponseEntity.badRequest().body(response));
					});		
		});
	}
	
	@PutMapping("/{id}")
	public Mono<ResponseEntity<Teacher>> updateStudent(@RequestBody Teacher teacher, @PathVariable String id)
	{
		return service.findByIdTeacher(id)
				.flatMap(t -> {
					t.setNamesTeacher(teacher.getNamesTeacher());
					t.setSurnamesTeacher(teacher.getSurnamesTeacher());
					t.setTypeDocument(teacher.getTypeDocument());
					t.setNumberDocument(teacher.getNumberDocument());
					return service.saveTeacher(t);
				}).map(s -> ResponseEntity.created(URI.create("/api/teacher/".concat(s.getId())))
				  .contentType(MediaType.APPLICATION_JSON_UTF8)
				  .body(s))
				.defaultIfEmpty(ResponseEntity.notFound().build());
	}	
	
	@DeleteMapping("/{id}")
	public Mono<ResponseEntity<Void>> deleteTeacher(@PathVariable String id)
	{
		return service.findByIdTeacher(id).flatMap(t -> {
			return service.deleteTeacher(t).then(Mono.just(new ResponseEntity<Void>(HttpStatus.NO_CONTENT)));		
		}).defaultIfEmpty(new ResponseEntity<Void>(HttpStatus.NO_CONTENT));
	}
}
