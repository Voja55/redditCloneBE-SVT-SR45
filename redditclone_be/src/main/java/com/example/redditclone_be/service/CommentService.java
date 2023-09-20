package com.example.redditclone_be.service;

import com.example.redditclone_be.model.dto.CommentDTO;
import com.example.redditclone_be.model.entity.Comment;
import com.example.redditclone_be.model.entity.Post;
import com.example.redditclone_be.model.entity.User;

import java.util.List;

public interface CommentService {

    Comment createComment(CommentDTO newCom);
    Comment findCommentById(Long id);
    List<Comment> findCommentsByPost(String post);
    List<Comment> findCommentsByComment(Comment comment);
    List<Comment> findCommentsByUser(User user);
}
