package io.people.api.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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
	public Student getStudent(@PathVariable ("id") Long id) {
		return studentService.getStudent(id);
	}
	
	@PostMapping("/students")
	public void addStudent(@RequestBody Student student) {
		studentService.saveStudent(student);
	}
	
	@PutMapping("/course/{id}")
	public void delStudent(@RequestBody Student student,
						   @PathVariable ("id") Long id) {
		Student s = studentService.getStudent(id);
		s.setRut(student.getRut());
		s.setName(student.getName());
		s.setLastName(student.getLastName());
		s.setAge(student.getAge());
		s.setCourse(student.getCourse());
		studentService.saveStudent(s);
	}
	
	@DeleteMapping
	public void delStudent(@PathVariable ("id") Long id) {
		studentService.delStudent(id);
	}
}
