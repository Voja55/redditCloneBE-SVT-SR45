package com.example.redditclone_be.service;

import com.example.redditclone_be.model.dto.UserDTO;
import com.example.redditclone_be.model.entity.User;

import java.util.List;

public interface UserService {

    User createUser(UserDTO userDTO);

    List<User> getAllUsers();

    User findByUsername(String username);

    User saveUser(User user);

}
