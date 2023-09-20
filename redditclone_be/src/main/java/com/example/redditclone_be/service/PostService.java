package com.example.redditclone_be.service;

import com.example.redditclone_be.model.dto.PostDTO;
import com.example.redditclone_be.model.entity.Post;
import com.example.redditclone_be.model.entity.User;
import com.example.redditclone_be.model.entity.elasticEntities.PostES;

import java.util.List;

public interface PostService {

    PostES createPost(PostDTO postDTO);

    List<PostES> findPostsByCommunity(String commId);

    List<PostES> findPostsByUser(String user);

    List<PostES> findAllHome();

    PostES findPostById(String postId);
}
