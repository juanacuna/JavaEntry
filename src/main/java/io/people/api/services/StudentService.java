package io.people.api.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import io.people.api.models.Student;
import io.people.api.repositories.StudentRepo;

@Service
public class StudentService {

	@Autowired
	private StudentRepo studentRepo;
	
	public List<Student> allStundents(){
		return studentRepo.findAll();
	}
	
	public Page<Student> allStudents(Pageable pageable){
		return studentRepo.findAll(pageable);
	}
	
	public Student getStudent(Long id) {
		return studentRepo.findById(id).get();
	}
	
	public void saveStudent(Student s) {
		studentRepo.save(s);
	}
	
	public void delStudent(Long id) {
		studentRepo.deleteById(id);
	}

	public boolean validRut(String rut) {
		boolean isValid = false;
		if(rut != null && rut.trim().length() > 0) {
			try {
				rut = rut.replaceAll("[.]", "").replaceAll("-", "").trim().toUpperCase();
				char vDigit = rut.charAt(rut.length()-1);
				int tempRut = Integer.parseInt(rut.substring(0, rut.length() - 1));
				int m = 0, s = 1;
				for(;tempRut != 0; tempRut /= 10) {
					s = (s + tempRut % 10 * (9 - m++ % 6)) % 11;
				}
				if (vDigit == (char) (s !=0 ? s + 47 : 75)) {
					isValid = true;
				}
			} catch (Exception e) {
				return isValid;
			}
		}
		return isValid;
	}
	
	
	
	
	
}
