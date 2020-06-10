package com.demo.OnlineCourse.dto;

import com.demo.OnlineCourse.model.AppUser;

public class TokenResponseDto {

	String jwtToken;
	AppUser user;

	public TokenResponseDto(String jwtToken,AppUser user) {
		super();
		this.jwtToken = jwtToken;
		this.user =  user;
	}

	public String getJwtToken() {
		return jwtToken;
	}

	public void setJwtToken(String jwtToken) {
		this.jwtToken = jwtToken;
	}
	
	public AppUser getUser() {
		return user;
	}

	public void setUser(AppUser user) {
		this.user = user;
	}

}
