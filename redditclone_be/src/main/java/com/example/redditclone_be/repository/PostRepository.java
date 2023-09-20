package com.example.redditclone_be.repository;

import com.example.redditclone_be.model.entity.Community;
import com.example.redditclone_be.model.entity.Post;
import com.example.redditclone_be.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

//@Repository
public interface PostRepository {//extends JpaRepository<Post, Long> {
//
//    public List<Post> findAllByCommunity(Community community);
//    public List<Post> findAllByPostedBy(User user);
//    public Optional<Post> findFirstById(Long id);
}
