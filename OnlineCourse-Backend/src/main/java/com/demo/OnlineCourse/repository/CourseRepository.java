package com.demo.OnlineCourse.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.demo.OnlineCourse.model.Course;

public interface CourseRepository extends JpaRepository<Course, Integer> {

}
