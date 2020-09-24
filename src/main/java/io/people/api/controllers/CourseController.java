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
import io.people.api.services.CourseService;

@RestController
public class CourseController {

	//Injection of Course Service
	@Autowired
	private CourseService courseService;
	
	// This method returns all courses and use HttpHeaders (headers)
	@GetMapping("/courses/all")
	public ResponseEntity<?> allCourse() {
		return ResponseEntity.ok().headers(courseService.httpHeader())
				.body(courseService.allCourses());
	}
	
	// This method returns all courses and use HttpHeaders (headers), paged by 5(default) or more items and can be sorted by name, code or id(default)
	@GetMapping("/courses")
	public ResponseEntity<Page<Course>> coursesPage(@RequestParam Optional<Integer> page,
								   @RequestParam Optional <String> sortBy){
		return ResponseEntity.ok().headers(courseService.httpHeader()).body(courseService.allCourses(PageRequest.of(
				page.orElse(0), 5, Sort.Direction.ASC, sortBy.orElse("id"))));
	}
	
	// This method call and show a course by Id. If not exist return 404 status
	@GetMapping("/courses/{id}")
	public ResponseEntity<?> getCourse(@PathVariable Long id) {
		try {
			return ResponseEntity.ok().headers(courseService.httpHeader())
					.body(courseService.getCurse(id));
		} catch (NoSuchElementException e) {
			return ResponseEntity.status(404).body("Course not Found!");
		}
	}
	
	// This method is used for save a new Course item, if is Success return 201 status
	@PostMapping("/courses")
	public ResponseEntity<?> addCourse(@Valid @RequestBody Course course) {
		courseService.saveCourse(course);
		return ResponseEntity.status(201).headers(courseService.httpHeader()).body("Created Ok!");
	}
	
	// This method call and Update a Course by Id.  If not exist return 404 status
	@PutMapping("/courses/{id}")
	public ResponseEntity<?> updateCourse(@Valid @RequestBody Course course,
							 @PathVariable ("id") Long id) {
		try {
			Course c = courseService.getCurse(id);
			c.setName(course.getName());
			c.setCode(course.getCode());
			courseService.saveCourse(c);
			return ResponseEntity.status(200).headers(courseService.httpHeader()).body("Updated Ok!");
		} catch (NoSuchElementException e) {
			return ResponseEntity.status(404).body("Course not Found!");
		}
	}
	
	// This method find a course by id and delete him. If  not exist return 404 status
	@DeleteMapping("/courses/{id}")
	public ResponseEntity<?> delCourse(@PathVariable ("id") Long id) {
		try {
			courseService.delCourse(courseService.getCurse(id).getId());
			return ResponseEntity.status(200).body("Course has been deleted!");
		} catch (NoSuchElementException e) {
			return ResponseEntity.status(404).body("Course not Found!");
		}
	}
}