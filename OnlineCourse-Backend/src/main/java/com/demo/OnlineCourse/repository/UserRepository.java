package com.demo.OnlineCourse.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.demo.OnlineCourse.model.AppUser;

public interface UserRepository extends JpaRepository<AppUser, Long> {
	
	Optional<AppUser> findByUserName(String userName);
	
//	Optional<AppUser> findUser(String userName);

}
