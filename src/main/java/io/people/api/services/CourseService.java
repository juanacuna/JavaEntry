package io.people.api.services;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import io.people.api.models.Course;
import io.people.api.repositories.CourseRepo;

@Service
public class CourseService {

	@Autowired // Injection of CourseRepo
	private CourseRepo courseRepo;
	
	// Service for List all Courses
	public List<Course> allCourses(){
		return courseRepo.findAll();
	}
	
	//Service for List all Courses (paged)
	public Page<Course> allCourses(Pageable pageable){
		return courseRepo.findAll(pageable);
	}
	
	// Service for get a Course finding by Id
	public Course getCurse(Long id) {
		return courseRepo.findById(id).get();
	}
	
	// Service for know if a Course exist or not
	public Boolean existCourse(String name) {
		return courseRepo.existsByName(name);
	}
	
	// Service for Save a New Course
	public void saveCourse(Course c) {
		courseRepo.save(c);
	}
	
	// Service for Delete a Course finding by Id
	public void delCourse(Long id) {
		courseRepo.deleteById(id);
	}
	
	// Service for a Get a Course finding by Course Name
	public Course getCourseByName(String name) {
		return courseRepo.getByName(name);
	}
	
	// Service for set HttpHeaders (Accept and ContentType)
	public HttpHeaders httpHeader() {
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		headers.setContentType(MediaType.APPLICATION_JSON);
		return headers;
	}
	
}
