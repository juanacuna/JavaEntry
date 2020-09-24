package io.people.api.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import io.people.api.models.Course;

@Repository
public interface CourseRepo extends JpaRepository<Course, Long>{
	
	List<Course> findAll(); //Returns a list of Courses 
	
	Page<Course> findAll(Pageable pageable); // Returns a paged list of courses
	
	Boolean existsByName(String name); //Find by Course name and Returns if a course exists (true or false)
	
	Course getByName(String name); // Find by Course_name and Return a Course 
}
