package com.appsdeveloper.usersapi.oneureka.ui.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.appsdeveloper.usersapi.oneureka.ui.service.UsersService;


@Configuration
@EnableWebSecurity
public class WebSecurity extends WebSecurityConfigurerAdapter{
	
	private UsersService usersService;
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	private Environment environment;
	
	@Autowired
	public WebSecurity(UsersService usersService, BCryptPasswordEncoder bCryptPasswordEncoder, Environment environment) {
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
		this.usersService = usersService;
		this.environment = environment;
	}
	
	
	@Override
	protected void configure(HttpSecurity http) throws Exception{
		http.csrf().disable();     //since we are going to use the jwt token for the security purpose
		http.authorizeRequests().antMatchers("/users/**").permitAll()       // http.authorizeRequests().antMatchers("/**").hasIpAddress(Environment.getProperty("gateway.ip")) -- for api ip address
		.and()
		.addFilter(getAuthenticationFilter())
		;
		http.headers().frameOptions().disable();   //so now H2 console could be visible
	}
	
	private AuthenticationFilter getAuthenticationFilter() throws Exception
	{
		AuthenticationFilter authenticationFilter = new AuthenticationFilter(usersService, environment, authenticationManager());
		
		// the below can also be set as done in the authentication autowire constructor  -- commenting below
//		authenticationFilter.setAuthenticationManager(authenticationManager());    //getAuthenticationManager() is in AuthenticationFilter class
		
		//can also add the below for the custom login URL -
//		authenticationFilter.setFilterProcessesUrl("/users/login");     --> when used then, the login willbe accessible on this URL only, else on default URL ("http://localhost:<PORT>/<application_name>/login") ==> http://infinity:8082/user-api/login
		
		return authenticationFilter;
	}
	
	//for specifying the name of the service which will be accessing the data for the validation part of the user details.
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception
	{
		auth.userDetailsService(usersService).passwordEncoder(bCryptPasswordEncoder); //taking the details
		
	}
}
