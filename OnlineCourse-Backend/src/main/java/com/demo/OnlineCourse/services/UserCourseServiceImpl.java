package com.demo.OnlineCourse.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.OnlineCourse.exception.CustomException;
import com.demo.OnlineCourse.model.AppUser;
import com.demo.OnlineCourse.model.Course;
import com.demo.OnlineCourse.repository.CourseRepository;
import com.demo.OnlineCourse.repository.UserRepository;

@Service
public class UserCourseServiceImpl implements UserCourseService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private CourseRepository courseRepository;

	@Override
	public void addCourse(String userName, Integer courseId) {
		Optional<AppUser> appUser = userRepository.findByUserName(userName);
		if (appUser.isPresent()) {
			Optional<Course> course = courseRepository.findById(courseId);
			if (course.isPresent()) {
				if(appUser.get().getCourses().contains(course.get())){
					throw new CustomException("Course Already Present in your course List.");
				}
				else {
					appUser.get().getCourses().add(course.get());
				}
				userRepository.save(appUser.get());
			} else {
				throw new CustomException("Invalid Course Id entered.");
			}
		} else {
			throw new CustomException("Invalid User tried adding a course");
		}
	}

	@Override
	public List<Course> getAllCourseForAUser(String userName) {
		Optional<AppUser> appUser = userRepository.findByUserName(userName);
		if (!appUser.isPresent()) {
			throw new CustomException("Invalid User");
		}
		return appUser.get().getCourses();

	}

	@Override
	public void deleteCourse(String userName, Integer courseId) {
		Optional<AppUser> appUser = userRepository.findByUserName(userName);
		if (appUser.isPresent()) {
			Optional<Course> course = courseRepository.findById(courseId);
			if (course.isPresent()) {
				appUser.get().getCourses().remove(course.get());
				userRepository.save(appUser.get());
			} else {
				throw new CustomException("Invalid Course Id entered.");
			}
		} else {
			throw new CustomException("Invalid User tried deleting a course");
		}
	}

}
