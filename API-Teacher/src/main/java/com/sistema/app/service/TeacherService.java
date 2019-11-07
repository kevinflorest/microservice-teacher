package com.sistema.app.service;

import com.sistema.app.models.Teacher;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface TeacherService  {

	Flux<Teacher> findAllTeacher();
	
	Mono<Teacher> findByIdTeacher(String id);
	
	Mono<Teacher> saveTeacher(Teacher teacher);
	
	Mono<Void> deleteTeacher(Teacher teacher);
	
	Flux<Teacher> findAllStudentByName(String namesTeacher);
	
}
