package com.demo.OnlineCourse.services;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.OnlineCourse.exception.CustomException;
import com.demo.OnlineCourse.model.Course;
import com.demo.OnlineCourse.repository.CourseRepository;

@Service
public class CourseServiceImpl implements CourseService{
	
	@Autowired
	private CourseRepository courseRepository;
	
	@Override
	public void addCourse(Course course) {
		validateCourse(course);
		courseRepository.save(course);
	}
	
	@Override
	public List<Course> getAllCourses() {
		return courseRepository.findAll();
	}

	@Override
	public void updateCourse(Course course) {
		validateCourse(course);
		courseRepository.save(course);
	}

	@Override
	public void deleteCourse(Integer courseId) {
		Optional<Course> course=courseRepository.findById(courseId);
		if(course.isPresent()) {
			courseRepository.delete(course.get());			
		}
		else {
			throw new CustomException("Given Course with course Id does not exists in Database.");
		}
	}

	private void validateCourse(Course course) {
		if(Objects.isNull(course)) {
			throw new CustomException("Course can not be empty.");
		}
		if(Objects.isNull(course.getCourseName())){
			throw new CustomException("Course Name can not be empty.");
		}
		if(Objects.isNull(course.getCourseDuration())){
			throw new CustomException("Course Duration can not be empty.");
		}
		if(Objects.isNull(course.getCoursePrice())){
			throw new CustomException("Course Price can not be empty.");
		}	
	}
}
