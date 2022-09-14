package com.example.redditclone_be.repository;

import com.example.redditclone_be.model.entity.Comment;
import com.example.redditclone_be.model.entity.Post;
import com.example.redditclone_be.model.entity.Reaction;
import com.example.redditclone_be.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReactionRepository extends JpaRepository<Reaction, Long> {

    public List<Reaction> findAllByReactingOnPost(Post post);
    public List<Reaction> findAllByReactingOnCom(Comment comment);
    public List<Reaction> findAllByMadeBy(User user);
}
