package io.people.api.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import io.people.api.models.Course;

@Repository
public interface CourseRepo extends JpaRepository<Course, Long>{
	
	List<Course> findAll();
	Page<Course> findAll(Pageable pageable);
	Boolean existsByName(String name);
	Course getByName(String name);
}
