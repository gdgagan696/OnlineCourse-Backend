package com.demo.OnlineCourse.services;

import java.util.List;

import com.demo.OnlineCourse.model.Course;

public interface UserCourseService {

	public void addCourse(String userName,Integer courseId);
	public List<Course> getAllCourseForAUser(String userName);
	public void deleteCourse(String userName,Integer courseId);
	
}
