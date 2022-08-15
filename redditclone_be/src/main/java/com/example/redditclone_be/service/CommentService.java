package com.example.redditclone_be.service;

import com.example.redditclone_be.model.entity.Comment;

public interface CommentService {

    Comment findCommentById(Long id);
}
