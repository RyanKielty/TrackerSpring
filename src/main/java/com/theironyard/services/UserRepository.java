package com.theironyard.services;

import com.theironyard.entities.User;
import org.springframework.data.repository.CrudRepository;


/**
 * Created by ryankielty on 1/27/17.
 */
public interface UserRepository extends CrudRepository <User, Integer> {
    User findFirstByName(String userName);
}
