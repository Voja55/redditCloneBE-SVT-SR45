package com.example.redditclone_be.service;

import com.example.redditclone_be.model.dto.UserDTO;
import com.example.redditclone_be.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserService extends JpaRepository<User, Long> {

}
