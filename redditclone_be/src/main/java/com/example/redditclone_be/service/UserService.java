package com.example.redditclone_be.service;

import com.example.redditclone_be.model.dto.UserDTO;
import com.example.redditclone_be.model.entity.User;

public interface UserService {

    User createUser(UserDTO userDTO);

    Boolean deleteUser(Long userID);

    User findUserByUsername(UserDTO userDTO);

    User updateUser(UserDTO userDTO);


}
