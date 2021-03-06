package com.demo.OnlineCourse.services;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.demo.OnlineCourse.dto.CustomUserDetails;
import com.demo.OnlineCourse.exception.CustomException;
import com.demo.OnlineCourse.model.AppUser;
import com.demo.OnlineCourse.repository.UserRepository;

@Service
public class CustomUserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {		
		Optional<AppUser> appUser=userRepository.findByUserName(username);
		appUser.orElseThrow(()->new CustomException(String.format("User with username %s not registered in system.", username)));
		return appUser.map(CustomUserDetails::new).get();
	}
	
	public void saveNewUser(AppUser appUser) {
		validateUser(appUser);
		appUser.setPassword(bCryptPasswordEncoder.encode(appUser.getPassword()));
		userRepository.save(appUser);
	}
	
	public AppUser getUser(String userName) throws UsernameNotFoundException {
		return userRepository.findByUserName(userName).get();
	}
	
	public void updatePassword(String userName,Map<String, String> password) throws UsernameNotFoundException {
		Optional<AppUser> loggedInUser=userRepository.findByUserName(userName);
		if(loggedInUser.isPresent()) {
			String oldPassword=password.get("oldPassword");
			String newPassword=password.get("newPassword");
			String reEnterPassword=password.get("rePassword");
			if(!newPassword.equals(reEnterPassword)) {
				throw new CustomException("New Password and re-entered password not matched.");
			}
			if(bCryptPasswordEncoder.matches(oldPassword,loggedInUser.get().getPassword())) {
				loggedInUser.get().setPassword(bCryptPasswordEncoder.encode(newPassword));
				userRepository.save(loggedInUser.get());
			}
			else {
				throw new CustomException("Old Password not correct.");
			}
		}
		else {
			throw new CustomException("Invalid User,User does not exists.");
		}
	}
	
	public AppUser updateUserInfo(String userName,Map<String, String> userInfo) throws UsernameNotFoundException {
		Optional<AppUser> loggedInUser=userRepository.findByUserName(userName);
		if(loggedInUser.isPresent()) {
			String firstName=userInfo.get("firstName");
			String lastName=userInfo.get("lastName");
			String regNo=userInfo.get("regNo");
			String email=userInfo.get("email");
			String userRole=userInfo.get("roles");
			String newUserName=userInfo.get("userName");
			validateInfo(firstName,regNo,email,newUserName,userRole);
			loggedInUser.get().setFirstName(firstName);
			loggedInUser.get().setLastName(lastName);
			loggedInUser.get().setRegNo(regNo);
			loggedInUser.get().setEmail(email);
			loggedInUser.get().setUserRole(userRole);
			loggedInUser.get().setUserName(newUserName);
			AppUser updatedUser=userRepository.save(loggedInUser.get());
			return updatedUser;
		}
		else {
			throw new CustomException("Invalid User,User does not exists.");
		}
	}
	
	private void validateUser(AppUser user) {
		if(Objects.isNull(user)) {
			throw new CustomException("User can not be empty.");
		}
		if(Objects.isNull(user.getFirstName())) {
			throw new CustomException("User first name can not be empty.");
		}
		if(Objects.isNull(user.getRegNo())) {
			throw new CustomException("User reg number can not be empty.");
		}
		if(Objects.isNull(user.getEmail())) {
			throw new CustomException("User email can not be empty.");
		}
		if(Objects.isNull(user.getUserName())) {
			throw new CustomException("Username can not be empty.");
		}
		if(Objects.isNull(user.getPassword())) {
			throw new CustomException("User password can not be empty.");
		}
		if(Objects.isNull(user.getUserRole())) {
			throw new CustomException("User role can not be empty.");
		}
	}

	private void validateInfo(String firstName,String regNo,String email,String userName,String userRole) {
		if(Objects.isNull(firstName)) {
			throw new CustomException("User first name can not be empty.");
		}
		if(Objects.isNull(regNo)) {
			throw new CustomException("User reg number can not be empty.");
		}
		if(Objects.isNull(email)) {
			throw new CustomException("User email can not be empty.");
		}
		if(Objects.isNull(userName)) {
			throw new CustomException("Username can not be empty.");
		}
		if(Objects.isNull(userRole)) {
			throw new CustomException("User role can not be empty.");
		}
	}
}
