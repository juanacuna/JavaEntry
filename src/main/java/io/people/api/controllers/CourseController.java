package io.people.api.controllers;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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

	@Autowired
	private CourseService courseService;
	
	/*@GetMapping("/courses/all")
	public List<Course> allCourses(){
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		return courseService.allCourses();
	}*/
	@GetMapping("/courses/all")
	public ResponseEntity<?> allCourse() {
		List<Course> c = courseService.allCourses();
		return new ResponseEntity<List<Course>>(c, HttpStatus.OK);
	}
	
	
	@GetMapping("/courses")
	public Page<Course> coursesPage(@RequestParam Optional<Integer> page,
								   @RequestParam Optional <String> sortBy){
		return courseService.allCourses(PageRequest.of(
				page.orElse(0), 5, Sort.Direction.ASC, sortBy.orElse("id")));
	}
	
	@GetMapping("/courses/{id}")
	public ResponseEntity<Course> getCourse(@PathVariable Long id) {
		try {
			Course c = courseService.getCurse(id);
			return new ResponseEntity<Course>(c, HttpStatus.OK);
		} catch (NoSuchElementException e) {
			return new ResponseEntity<Course>(HttpStatus.NOT_FOUND);
		}
	}
	
	@PostMapping("/courses")
	public ResponseEntity<?> addCourse(@Valid @RequestBody Course course) {
		courseService.saveCourse(course);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}
	
	@PutMapping("/courses/{id}")
	public ResponseEntity<?> updateCourse(@RequestBody Course course,
							 @PathVariable ("id") Long id) {
		try {
			Course c = courseService.getCurse(id);
			c.setName(course.getName());
			c.setCode(course.getCode());
			courseService.saveCourse(c);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (NoSuchElementException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@DeleteMapping("/courses/{id}")
	public ResponseEntity<?> delCourse(@PathVariable ("id") Long id) {
		try {
			courseService.delCourse(id);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (NoSuchElementException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
}