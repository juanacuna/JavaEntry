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

import io.people.api.models.Course;
import io.people.api.services.CourseService;

@RestController
public class CourseController {

	@Autowired
	private CourseService courseService;
	
	@GetMapping("/courses/all")
	public List<Course> allCourses(){
		return courseService.allCourses();
	}
	
	@GetMapping("/courses")
	public Page<Course> coursesPage(@RequestParam Optional<Integer> page,
								   @RequestParam Optional <String> sortBy){
		return courseService.allCourses(PageRequest.of(
				page.orElse(0), 5, Sort.Direction.ASC, sortBy.orElse("id")));
	}
	
	@GetMapping("/courses/{id}")
	public Course getCourse(@PathVariable Long id) {
		return courseService.getCurse(id);
	}
	
	@PostMapping("/courses")
	public void addCourse(@RequestBody Course course) {
		courseService.saveCourse(course);
	}
	
	@PutMapping("/courses/{id}")
	public void updateCourse(@RequestBody Course course,
							 @PathVariable ("id") Long id) {
		Course c = courseService.getCurse(id);
		c.setName(course.getName());
		c.setCode(course.getCode());
		courseService.saveCourse(c);
	}
	
	@DeleteMapping("/courses/{id}")
	public void delCourse(@PathVariable ("id") Long id) {
		courseService.delCourse(id);
	}
	
}
