package com.demo.OnlineCourse.controllers;

import java.security.Principal;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.demo.OnlineCourse.dto.CustomUserDetails;
import com.demo.OnlineCourse.dto.ResponseDto;
import com.demo.OnlineCourse.dto.TokenResponseDto;
import com.demo.OnlineCourse.exception.CustomException;
import com.demo.OnlineCourse.model.AppUser;
import com.demo.OnlineCourse.services.CustomUserDetailsServiceImpl;
import com.demo.OnlineCourse.util.JwtUtil;

@RestController
@RequestMapping("/users")
public class UserController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private CustomUserDetailsServiceImpl customUserDetailsService;

	@Autowired
	private JwtUtil jwtUtil;

	@PostMapping("/authenticate")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<TokenResponseDto> authenticateUser(@RequestBody(required = true) AppUser appUser)
			throws Exception {
		Authentication authentication = null;
		try {
			authentication = authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(appUser.getUserName(), appUser.getPassword()));

		} catch (BadCredentialsException e) {
			throw new CustomException("Bad Credentials,Invalid Password.");
		}
//		final UserDetails userDetails=customUserDetailsService.loadUserByUsername(appUser.getUserName());
		final CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
		final String jwtToken = jwtUtil.generateToken(userDetails);
		final AppUser loggedInUser = customUserDetailsService.getUser(userDetails.getUsername());
		return new ResponseEntity<>(new TokenResponseDto(jwtToken, loggedInUser), HttpStatus.OK);
	}

	@PostMapping("/createUser")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<ResponseDto> signUp(@RequestBody(required = true) AppUser user) throws CustomException {
		String msg = "User Registered Successfully.";
		customUserDetailsService.saveNewUser(user);
		return new ResponseEntity<>(new ResponseDto(msg), HttpStatus.CREATED);
	}

	@PreAuthorize("hasAnyAuthority('ADMIN','USER')")
	@GetMapping("/getUser")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<AppUser> getUserInfo(@AuthenticationPrincipal Principal principal) throws CustomException {
		return new ResponseEntity<>(customUserDetailsService.getUser(principal.getName()), HttpStatus.OK);
	}

	@PreAuthorize("hasAnyAuthority('ADMIN','USER')")
	@PutMapping("/changePassword")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<ResponseDto> changePassword(@AuthenticationPrincipal Principal principal,
			@RequestBody(required = true) Map<String, String> password) throws CustomException {
		String msg = "Password Updated Successfully.";
		customUserDetailsService.updatePassword(principal.getName(), password);
		return new ResponseEntity<>(new ResponseDto(msg), HttpStatus.OK);
	}

	@PreAuthorize("hasAnyAuthority('ADMIN','USER')")
	@PutMapping("/updateInfo")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<?> updateInfo(@AuthenticationPrincipal Principal principal,
			@RequestBody(required = true) Map<String, String> userInfo) throws CustomException {
		AppUser appUser = customUserDetailsService.updateUserInfo(principal.getName(), userInfo);
		if (appUser.getUserName().equals(principal.getName())) {
			return new ResponseEntity<>(customUserDetailsService.updateUserInfo(principal.getName(), userInfo),
					HttpStatus.OK);
		} else {
			final String newJwtToken = jwtUtil.refreshJwtToken(appUser.getUserName());
			return new ResponseEntity<>(new TokenResponseDto(newJwtToken, appUser), HttpStatus.OK);
		}
	}

}
