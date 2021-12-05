package com.appsdeveloper.usersapi.oneureka.ui.service;

import java.util.ArrayList;
import java.util.UUID;

import org.bouncycastle.crypto.generators.BCrypt;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.appsdeveloper.usersapi.oneureka.ui.data.UserEntity;
import com.appsdeveloper.usersapi.oneureka.ui.data.UsersRepository;
import com.appsdeveloper.usersapi.oneureka.ui.shared.UserDto;

@Service
public class UsersServiceImpl implements UsersService {

	@Autowired
	UsersRepository userRepository;

	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;

	/*
	 * can use the same using the constructor autowiring -- no need to autowire the
	 * above variables.
	 * 
	 * @Autowired public UserServiceImpl (Repository userRepository,
	 * BCryptPasswordEncoder bCryptPasswordEncoder){ this.userRepository =
	 * userRepository; this.bCryptPasswordEncoder = bCryptPasswordEncoder; }
	 * 
	 */

	@Override
	public UserDto createUser(UserDto userDetails) {
		userDetails.setUserId(UUID.randomUUID().toString());
		userDetails.setEncryptedPassword(bCryptPasswordEncoder.encode(userDetails.getPassword()));

		// for mapping the values from dto to entity
		ModelMapper mapper = new ModelMapper();
		mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STANDARD);

		UserEntity userEntity = mapper.map(userDetails, UserEntity.class); // mapping from source to destination - from
																			// userDto model to entity
//		userEntity.setEncryptedPassword("test");     --> no need as encoding done above
		userRepository.save(userEntity);

		UserDto returnValue = mapper.map(userEntity, UserDto.class);

		return returnValue;
	}

	// this method will be used when the authentication would be needed to be done
	// by the username
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// it doesnot contain any method to find the method by username or by email from
		// repository/database-- so custom method needs to be written in repository
		// interface
		UserEntity userEntity = userRepository.findByEmail(username); // here email is being used as the username

		if (userEntity == null)
			throw new UsernameNotFoundException(username);

		return new User(userEntity.getEmail(), userEntity.getEncryptedPassword(), true, true, true, true,
				new ArrayList<>()); // if the 3rd, boolean value is true then teh user can login and if false then
									// the user can't login
	}

	@Override
	public UserDto getUserDetailsByEmail(String email) {
		UserEntity userEntity = userRepository.findByEmail(email);

		if (userEntity == null)
			throw new UsernameNotFoundException(email);

		return new ModelMapper().map(userEntity, UserDto.class);
	}

}
