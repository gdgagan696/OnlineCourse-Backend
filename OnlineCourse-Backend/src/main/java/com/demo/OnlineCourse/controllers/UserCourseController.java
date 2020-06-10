package com.demo.OnlineCourse.controllers;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.demo.OnlineCourse.dto.ResponseDto;
import com.demo.OnlineCourse.exception.CustomException;
import com.demo.OnlineCourse.model.Course;
import com.demo.OnlineCourse.services.UserCourseService;

@RestController
@RequestMapping("user")
public class UserCourseController {

	@Autowired
	private UserCourseService userCourseService;

	@PreAuthorize("hasAuthority('USER')")
	@PostMapping("/addCourse/{courseId}")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<ResponseDto> addCourse(@PathVariable(required = true) Integer courseId,
			@AuthenticationPrincipal Principal principal) throws CustomException {
		String msg = "Course successfully added to your Course List.";
		userCourseService.addCourse(principal.getName(), courseId);
		return new ResponseEntity<>(new ResponseDto(msg), HttpStatus.CREATED);
	}

	@PreAuthorize("hasAuthority('USER')")
	@GetMapping(path = "/getCourses", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<List<Course>> getCourses(@AuthenticationPrincipal Principal principal) throws CustomException {
		return new ResponseEntity<>(userCourseService.getAllCourseForAUser(principal.getName()), HttpStatus.OK);
	}

	@PreAuthorize("hasAuthority('USER')")
	@DeleteMapping("/deleteCourse/{courseId}")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<ResponseDto> deleteCourse(@PathVariable(required = true) Integer courseId,
			@AuthenticationPrincipal Principal principal) throws CustomException {
		String msg = "Course Successfully Deleted from your Course List.";
		userCourseService.deleteCourse(principal.getName(), courseId);
		return new ResponseEntity<>(new ResponseDto(msg), HttpStatus.OK);
	}
}
