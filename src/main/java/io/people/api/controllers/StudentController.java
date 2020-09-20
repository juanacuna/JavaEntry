package io.people.api.controllers;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.people.api.models.Student;
import io.people.api.services.StudentService;

@RestController
public class StudentController {

	@Autowired
	private StudentService studentService;
	
	@GetMapping("/students/all")
	public List<Student> allStudents(){
		return studentService.allStundents();
	}
	
	@GetMapping("/students")
	public Page<Student> studentsPage(@RequestParam Optional<Integer> page,
									  @RequestParam Optional<String> sortBy){
		return studentService.allStudents(PageRequest.of(
				page.orElse(0), 5, Sort.Direction.ASC, sortBy.orElse("id")));
	}
	
	@GetMapping("/students/{id}")
	public ResponseEntity<Student> getStudent(@PathVariable ("id") Long id) {
		try {
			Student s = studentService.getStudent(id);
			return new ResponseEntity<Student>(s,HttpStatus.OK);
		} catch (NoSuchElementException e) {
			return new ResponseEntity<Student>(HttpStatus.NOT_FOUND);
		} 
	}
	
	@PostMapping("/students")
	public ResponseEntity<?> addStudent(@Valid @RequestBody Student student) {
		studentService.saveStudent(student);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}
	
	@PutMapping("/course/{id}")
	public ResponseEntity<?> delStudent(@RequestBody Student student,
						   @PathVariable ("id") Long id) {
		Student s = studentService.getStudent(id);
		
		try {
			s.setRut(student.getRut());
			s.setName(student.getName());
			s.setLastName(student.getLastName());
			s.setAge(student.getAge());
			s.setCourse(student.getCourse());
			studentService.saveStudent(s);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (NoSuchElementException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@DeleteMapping
	public ResponseEntity<?> delStudent(@PathVariable ("id") Long id) {
		try {
			studentService.delStudent(id);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (NoSuchElementException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
}