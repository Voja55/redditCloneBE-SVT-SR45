package com.example.redditclone_be.service.serviceImplemented;

import com.example.redditclone_be.model.dto.CommentDTO;
import com.example.redditclone_be.model.entity.Comment;
import com.example.redditclone_be.model.entity.Post;
import com.example.redditclone_be.model.entity.User;
import com.example.redditclone_be.repository.CommentRepository;
import com.example.redditclone_be.service.CommentService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CommentServiceImplements implements CommentService {

    final CommentRepository commentRepository;

    public CommentServiceImplements(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    @Override
    public Comment createComment(CommentDTO newCom) {
        Comment newComment = new Comment();
        newComment.setText(newCom.getText());
        newComment.setDeleted(false);
        newComment.setRepliesTo(newCom.getRepliesTo());
        newComment.setCommentsOn(newCom.getCommentsOn());
        newComment.setBelongsTo(newCom.getBelongsTo());

        newComment = commentRepository.save(newComment);

        return newComment;

    }

    @Override
    public Comment findCommentById(Long id) {

        Optional<Comment> comment = commentRepository.findFirstById(id);
        if(comment.isPresent()){
            return comment.get();
        }
        return null;
    }

    @Override
    public List<Comment> findCommentsByPost(Post post) {
        return commentRepository.findAllByCommentsOn(post);
    }

    @Override
    public List<Comment> findCommentsByComment(Comment comment) {
        return commentRepository.findAllByRepliesTo(comment);
    }

    @Override
    public List<Comment> findCommentsByUser(User user) {
        return commentRepository.findAllByBelongsTo(user);
    }
}
