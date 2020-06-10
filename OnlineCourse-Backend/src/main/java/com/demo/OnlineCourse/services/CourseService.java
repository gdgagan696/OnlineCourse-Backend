package com.demo.OnlineCourse.services;

import java.util.List;

import com.demo.OnlineCourse.model.Course;

public interface CourseService {
	
	public void addCourse(Course course);
	public List<Course> getAllCourses();
	public void updateCourse(Course course);
	public void deleteCourse(Integer courseId);

}
