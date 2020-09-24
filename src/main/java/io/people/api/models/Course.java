package io.people.api.models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.validation.constraints.Size;


// creation of the courses table and Course Entity
@Entity
@Table(name="courses")
public class Course {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	@Size(max = 4, message = "Course code should be up to 4 characters")
	private String code;
	
	//Simple relationship with the students table
	@OneToMany(cascade = CascadeType.ALL,
			orphanRemoval = true)
	private List<Student> students = new ArrayList<>();
	
	@Column(updatable = false)
	private Date createdAt;
	private Date updatedAt;
	
	// Constructor
	public Course() {
	}

	@PrePersist
	protected void onCreate() {
		this.createdAt = new Date();
	}
	
	@PreUpdate
	protected void onUpdate() {
		this.updatedAt = new Date();
	}

	// Getters and Setters
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	public List<Student> getStudents() {
		return students;
	}

	public void setStudents(List<Student> students) {
		this.students = students;
	}
	
	// Method for remove Student from Course List when is deleted
	public void removeStudent(Student s) {
		for (int i = 0; i < this.students.size(); i++) {
			Student student = this.students.get(i);
			if (student.getId() == s.getId()) {
				this.students.remove(i);
				return;
			}
		}
	}

}
