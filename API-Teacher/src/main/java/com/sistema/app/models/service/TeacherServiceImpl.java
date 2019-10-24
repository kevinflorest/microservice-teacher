package com.sistema.app.models.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sistema.app.models.dao.TeacherDAO;
import com.sistema.app.models.documents.Teacher;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class TeacherServiceImpl implements TeacherService {

	@Autowired
	private TeacherDAO tdao;
	
	@Override
	public Flux<Teacher> findAllTeacher() {
		return tdao.findAll();
	}

	@Override
	public Mono<Teacher> findByIdTeacher(String id) {
		return tdao.findById(id);
	}

	@Override
	public Mono<Teacher> saveTeacher(Teacher teacher) {
		return tdao.save(teacher);
	}

	@Override
	public Mono<Void> deleteTeacher(Teacher teacher) {
		return tdao.delete(teacher);
	}

	@Override
	public Flux<Teacher> findAllStudentByName(String namesTeacher) {
		return tdao.findAllByNameTeacher(namesTeacher);
	}

}
