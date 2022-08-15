package com.example.redditclone_be.service.serviceImplemented;

import com.example.redditclone_be.model.entity.Comment;
import com.example.redditclone_be.repository.CommentRepository;
import com.example.redditclone_be.service.CommentService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CommentServiceImplements implements CommentService {

    final CommentRepository commentRepository;

    public CommentServiceImplements(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    @Override
    public Comment findCommentById(Long id) {

        Optional<Comment> comment = commentRepository.findFirstById(id);
        if(comment.isPresent()){
            return comment.get();
        }
        return null;
    }
}
