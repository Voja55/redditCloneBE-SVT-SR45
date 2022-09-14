package com.example.redditclone_be.repository;

import com.example.redditclone_be.model.entity.Comment;
import com.example.redditclone_be.model.entity.Post;
import com.example.redditclone_be.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    Optional<Comment> findFirstById(Long id);
    List<Comment> findAllByRepliesTo(Comment comment);
    List<Comment> findAllByCommentsOn(Post post);
    List<Comment> findAllByBelongsTo(User user);

}
