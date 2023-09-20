package com.example.redditclone_be.repository;

import com.example.redditclone_be.model.entity.Community;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

//@Repository
public interface CommunityRepository { // extends JpaRepository<Community, Long> {

//    public Optional<Community> findFirstByName(String name);
//
//    @Query("SELECT c FROM Community c WHERE c.isSuspended = false")
//    public List<Community> findAllAvailable();
}
