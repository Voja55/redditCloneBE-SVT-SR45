package com.example.redditclone_be.repository;

import com.example.redditclone_be.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository  extends JpaRepository<User, Long> {

    public Optional<User> findFirstByUsername(String username);

}
