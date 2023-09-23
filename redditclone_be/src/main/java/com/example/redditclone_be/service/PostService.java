package com.example.redditclone_be.service;

import com.example.redditclone_be.model.dto.CommunityPdfDTO;
import com.example.redditclone_be.model.dto.PostDTO;
import com.example.redditclone_be.model.dto.PostPdfDTO;
import com.example.redditclone_be.model.entity.Post;
import com.example.redditclone_be.model.entity.Reaction;
import com.example.redditclone_be.model.entity.User;
import com.example.redditclone_be.model.entity.elasticEntities.PostES;

import java.io.IOException;
import java.util.List;

public interface PostService {

    PostES createPost(PostDTO postDTO);

    List<PostES> findPostsByCommunity(String commId);

    List<PostES> findPostsByUser(String user);

    List<PostES> findAllHome();

    PostES findPostById(String postId);

    List<PostES> searchPosts(String comm, String input, Long minKarma, Long maxKarma);

    boolean editKarma(Reaction reaction);

    void indexUploadedFilePost(PostPdfDTO postPdfDTO) throws IOException;
}
