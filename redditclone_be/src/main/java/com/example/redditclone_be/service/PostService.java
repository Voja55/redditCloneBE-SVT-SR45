package com.example.redditclone_be.service;

import com.example.redditclone_be.model.dto.PostDTO;
import com.example.redditclone_be.model.entity.Post;

import java.util.List;

public interface PostService {

    Post createPost(PostDTO postDTO);

    List<Post> findPostsByCommunity(Long commId);

    List<Post> findPostsByUser(Long userId);

    Post findPostById(Long postId);
}
