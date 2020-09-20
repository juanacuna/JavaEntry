package io.people.api.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
	
	public void saveCourse(Course c) {
		courseRepo.save(c);
	}
	
	public void delCourse(Long id) {
		courseRepo.deleteById(id);
	}
	
}
