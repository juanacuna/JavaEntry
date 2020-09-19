package io.people.api.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.people.api.models.Student;
import io.people.api.repositories.StudentRepo;

@Service
public class StudentService {

	@Autowired
	private StudentRepo studentRepo;
	
	public List<Student> allStundents(){
		return studentRepo.findAll();
	}
	
	public Student getStudent(Long id) {
		return studentRepo.findById(id).get();
	}
	
	public void saveStudent(Student s) {
		studentRepo.save(s);
	}
	
	public void delStudent(Long id) {
		studentRepo.deleteById(id);
	}

}
