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

	@Autowired
	private CourseRepo courseRepo;
	
	public List<Course> allCourses(){
		return courseRepo.findAll();
	}
	
	public Page<Course> allCourses(Pageable pageable){
		return courseRepo.findAll(pageable);
	}
	
	public Course getCurse(Long id) {
		return courseRepo.findById(id).get();
	}
	
	public Boolean existCourse(String name) {
		return courseRepo.existsByName(name);
	}
	
	public void saveCourse(Course c) {
		courseRepo.save(c);
	}
	
	public void delCourse(Long id) {
		courseRepo.deleteById(id);
	}
	
	public Course getCourseByName(String name) {
		return courseRepo.getByName(name);
	}
	
	public HttpHeaders httpHeader() {
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		headers.setContentType(MediaType.APPLICATION_JSON);
		return headers;
	}
	
}
