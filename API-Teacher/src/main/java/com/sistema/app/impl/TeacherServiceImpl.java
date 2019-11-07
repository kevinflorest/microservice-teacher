package com.sistema.app.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sistema.app.dao.TeacherDAO;
import com.sistema.app.models.Teacher;
import com.sistema.app.service.TeacherService;

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
