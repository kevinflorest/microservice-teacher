package com.sistema.app.models.dao;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;


import com.sistema.app.models.documents.Teacher;

import reactor.core.publisher.Flux;

public interface TeacherDAO extends ReactiveMongoRepository<Teacher, String> {
	
	@Query("{ 'namesTeacher' : ?0}")
	Flux<Teacher> findAllByNameTeacher(String namesTeacher);
	
}
