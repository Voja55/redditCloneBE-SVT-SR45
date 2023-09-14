package com.example.redditclone_be.service;

import com.example.redditclone_be.model.dto.PostDTO;
import com.example.redditclone_be.model.entity.Post;
import com.example.redditclone_be.model.entity.User;

import java.util.List;

public interface PostService {

    Post createPost(PostDTO postDTO);

    List<Post> findPostsByCommunity(Long commId);

    List<Post> findPostsByUser(User user);

    List<Post> findAllHome();

    Post findPostById(Long postId);
}
