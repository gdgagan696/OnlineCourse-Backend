package com.demo.OnlineCourse.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.demo.OnlineCourse.dto.ResponseDto;
import com.demo.OnlineCourse.exception.CustomException;
import com.demo.OnlineCourse.model.Course;
import com.demo.OnlineCourse.services.CourseService;

@RestController
@RequestMapping("/course")
public class CourseController {

	@Autowired
	private CourseService courseService;
	
	@PreAuthorize("hasAuthority('ADMIN')")
	@PostMapping("/addCourse")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<ResponseDto> addCourse(@RequestBody(required = true) Course course) throws CustomException{
		String msg="Course Added Successfully.";
		courseService.addCourse(course);
		return new ResponseEntity<>(new ResponseDto(msg) , HttpStatus.CREATED);
	}
	
	@PreAuthorize("hasAnyAuthority('ADMIN','USER')")
	@GetMapping(path = "/getCourses",produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<List<Course>> getCourses() {
		return new ResponseEntity<>(courseService.getAllCourses(), HttpStatus.OK);
	}
	
	@PreAuthorize("hasAuthority('ADMIN')")
	@PutMapping("/updateCourse")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<ResponseDto> updateCourse(@RequestBody(required = true) Course course)throws CustomException {
		String msg="Course Updated/Added Successfully.";
		courseService.updateCourse(course);
		return new ResponseEntity<>(new ResponseDto(msg) , HttpStatus.OK);	
	}
	
	@PreAuthorize("hasAuthority('ADMIN')")
	@DeleteMapping("/deleteCourse/{courseId}")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<ResponseDto> deleteCourse(@PathVariable(required = true) Integer courseId) throws CustomException{
		String msg="Course Deleted Successfully.";
		courseService.deleteCourse(courseId);
		return new ResponseEntity<>(new ResponseDto(msg) , HttpStatus.OK);	
	}
}
