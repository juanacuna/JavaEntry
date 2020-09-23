package io.people.api.controllers;

import java.util.NoSuchElementException;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.people.api.models.Course;
import io.people.api.models.Student;
import io.people.api.services.CourseService;
import io.people.api.services.StudentService;

@RestController
public class StudentController {

	@Autowired
	private StudentService studentService;
	
	@Autowired
	private CourseService courseService;
	
	@GetMapping("/students/all")
	public ResponseEntity<?>  allStudents(){
		return ResponseEntity.ok().headers(studentService.httpHeader())
				.body(studentService.allStundents());
	}
	
	@GetMapping("/students")
	public ResponseEntity<Page<Student>> studentsPage(@RequestParam Optional<Integer> page,
									  @RequestParam Optional<String> sortBy){
		return ResponseEntity.ok().headers(courseService.httpHeader())
				.body(studentService.allStudents(PageRequest.of(page.orElse(0),
						5, Sort.Direction.ASC, sortBy.orElse("id"))));
	}
	
	@GetMapping("/students/{id}")
	public ResponseEntity<?> getStudent(@PathVariable ("id") Long id) {
		try {
			return ResponseEntity.ok().headers(studentService.httpHeader())
					.body(studentService.getStudent(id));
		} catch (NoSuchElementException e) {
			return ResponseEntity.status(404).body("Student not Found!");
		} 
	}
	
	@PostMapping("/students")
	public ResponseEntity<?> addStudent(@Valid @RequestBody Student student) {
		if (studentService.validRut(student.getRut())) {
			if(courseService.existCourse(student.getCourse())) {
				Course c = courseService.getCourseByName(student.getCourse());
				c.getStudents().add(student);
				studentService.saveStudent(student);
				return ResponseEntity.status(201).headers(studentService.httpHeader()).body("Created Ok!");
			}else {
				return ResponseEntity
						.status(400).body("Course not exist, please check and try again");
			}
		}else {
			return ResponseEntity
					.status(400).body("Digit validator does not match the rut received or the format is invalid");
		}
	}
	
	@PutMapping("/students/{id}")
	public ResponseEntity<?> delStudent(@Valid @RequestBody Student student,
						   @PathVariable ("id") Long id) {
		if (studentService.validRut(student.getRut())) {
			if(courseService.existCourse(student.getCourse())) {
				try {
					Student s = studentService.getStudent(id);
					s.setRut(student.getRut());
					s.setName(student.getName());
					s.setLastName(student.getLastName());
					s.setAge(student.getAge());
					s.setCourse(student.getCourse());
					Course c = courseService.getCourseByName(student.getCourse());
					studentService.saveStudent(s);
					return ResponseEntity.status(200).headers(studentService.httpHeader()).body("Updated Ok!");
				} catch (NoSuchElementException e) {
					return ResponseEntity.status(404).body("Student not Found!");
				}
			}else {
				return ResponseEntity
						.status(400).body("Course not exist, please check and try again");
			}
		}else {
			return ResponseEntity
					.status(400).body("Digit validator does not match the rut received or the format is invalid");
		}
	}
	
	@DeleteMapping("/students/{id}")
	public ResponseEntity<?> delStudent(@PathVariable ("id") Long id) {
		try {
			Student s = studentService.getStudent(id);
			Course c = courseService.getCourseByName(s.getCourse());
			c.removeStudent(s);
			studentService.delStudent(id);
			return ResponseEntity.status(200).body("Studet has been deleted!");
		} catch (NoSuchElementException e) {
			return ResponseEntity.status(404).body("Student not Exist!");
		}
	}
}