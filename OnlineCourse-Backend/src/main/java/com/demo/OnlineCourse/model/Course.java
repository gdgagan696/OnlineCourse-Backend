package com.demo.OnlineCourse.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "course")
public class Course {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "course_id")
	Integer courseId;
	
	
	@Column(name = "course_name",nullable = false)
	String courseName;
	
	
	@Column(name = "course_duration",nullable = false)
	Integer courseDuration;
	
	
	@Column(name = "course_price",nullable = false)
	BigDecimal coursePrice;
	
	@ManyToMany(mappedBy = "courses",fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
	@JsonIgnore
	List<AppUser> users=new ArrayList<>();
	
	public Integer getCourseId() {
		return courseId;
	}
	public void setCourseId(Integer courseId) {
		this.courseId = courseId;
	}
	public String getCourseName() {
		return courseName;
	}
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}
	public Integer getCourseDuration() {
		return courseDuration;
	}
	public void setCourseDuration(Integer courseDuration) {
		this.courseDuration = courseDuration;
	}
	public BigDecimal getCoursePrice() {
		return coursePrice;
	}
	public void setCoursePrice(BigDecimal coursePrice) {
		this.coursePrice = coursePrice;
	}
	public List<AppUser> getUsers() {
		return users;
	}
	public void setUsers(List<AppUser> users) {
		this.users = users;
	}
	
	
}
