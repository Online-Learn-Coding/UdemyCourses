package com.appsdeveloper.usersapi.oneureka.ui.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.appsdeveloper.usersapi.oneureka.ui.model.LoginRequestModel;
import com.appsdeveloper.usersapi.oneureka.ui.service.UsersService;
import com.appsdeveloper.usersapi.oneureka.ui.shared.UserDto;
import com.fasterxml.jackson.databind.ObjectMapper;


import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

//this will be called everytime an authentication attempt is made   (this needs to be registered withthe httpsSecurity which would be done in WebSecurity file)
public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter{     //custom filter
	
	private UsersService usersService;
	private Environment environment;
	
	@Autowired
	public AuthenticationFilter(UsersService usersService,Environment environment, AuthenticationManager authenticationManager)
	{
		this.usersService = usersService;
		this.environment = environment;
		super.setAuthenticationManager(authenticationManager);
	}
	
	//overriding a method for authentication
	@Override
	public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res) throws AuthenticationException{
		try {
			LoginRequestModel creds = new ObjectMapper().readValue(req.getInputStream(),LoginRequestModel.class);  //taking the credentials here 
			
			//giving the username and password details of the user to find the user existence
			return getAuthenticationManager()
					.authenticate(new UsernamePasswordAuthenticationToken(
							creds.getEmail(), 
							creds.getPassword(),
							new ArrayList<>()));   //setAuthenticationManager() part will be done in the webSecurity file
			
		}catch(IOException e) {
			throw new RuntimeException();
		}
	}
	
	
	//Below will be called when the authentication would be successful (when the above method is successful)    --> this will be called by the spring framework by itself upon success authentication
	// for the jwt token and related details to be sent in response
	@Override
	protected void successfulAuthentication(HttpServletRequest req, HttpServletResponse res, FilterChain chain, Authentication auth) throws IOException, ServletException{
		String userName = ((User) auth.getPrincipal()).getUsername();   //returning the username - email
		UserDto userDetails = usersService.getUserDetailsByEmail(userName);    //returning the userDto  from the service class
		
		//Adding the part for the jwt token creation
		String token = Jwts.builder()
				.setSubject(userDetails.getUserId())   //setting the subject which is a userId
				.setExpiration(new Date(System.currentTimeMillis() + Long.parseLong(environment.getProperty("token.expiration_time"))))   //accessing the value from *.properties file using env*
				.signWith(SignatureAlgorithm.HS512, environment.getProperty("token.secret"))    //taking value from the *.properties file
				.compact();
				
		//Adding the above token to the response header to take it to the client application
		res.addHeader("token", token);
		res.addHeader("userId", userDetails.getUserId());
		
	}
}
