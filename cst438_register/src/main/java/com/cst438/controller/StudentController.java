package com.cst438.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.cst438.domain.Student;
import com.cst438.domain.StudentDTO;
import com.cst438.domain.StudentRepository;

@RestController
public class StudentController {
	
	@Autowired
	StudentRepository studentRepository;

	
	@PostMapping("/student")
	public StudentDTO createStudentDTO(Student s){
		
		StudentDTO ans = new StudentDTO();
		ans.student_id = s.getStudent_id()
		ans.name = s.getName();
		ans.email = s.getEmail();
		ans.statusCode = s.getStatusCode()
		ans.status = s.getStatus();
		return ans;
	}
	
	@GetMapping("/student")
	public StudentDTO getStudent(@RequestParam("email") String email) {
		
		Student student = studentRepository.findByEmail(email);
		StudentDTO result = null;
		if(student != null) {
			
			result = createStudentDTO(student);
			return result;
		}
		
		else {
			System.out.println("/schedule student not found. "+email);
			throw  new ResponseStatusException( HttpStatus.BAD_REQUEST, "Student not found. " );
		}
	}
	
	@PostMapping("/student")
	@Transactional
	public StudentDTO addStudent(@RequestParam("name") String name, @RequestParam("email") String email) {
		
		Student ans = studentRepository.findByEmail(email);
		if(ans == null) {
			
			ans = new Student();
			ans.setName(name);
			ans.setEmail(email);
			Student saved = studentRepository.save(ans);
			StudentDTO result = createStudentDTO(saved);
			return result;
		}
		else {
			throw  new ResponseStatusException( HttpStatus.BAD_REQUEST, "Student already exists in database.");
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
