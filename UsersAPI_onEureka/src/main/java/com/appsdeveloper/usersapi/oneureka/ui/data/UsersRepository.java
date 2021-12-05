package com.appsdeveloper.usersapi.oneureka.ui.data;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository        // this annotation could be removed as it could be created by using the constructor autowired
public interface UsersRepository extends CrudRepository<UserEntity, Long> {

	UserEntity findByEmail(String email);    //naming in this way, the framework will automatically create a select query to fetch elements
}
