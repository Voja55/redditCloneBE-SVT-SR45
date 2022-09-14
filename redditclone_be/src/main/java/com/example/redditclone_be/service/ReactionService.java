package com.example.redditclone_be.service;

import com.example.redditclone_be.model.dto.ReactDTO;
import com.example.redditclone_be.model.entity.Reaction;

import java.util.List;

public interface ReactionService {

    List<Reaction> findReactionsByPost(Long postId);
    List<Reaction> findReactionsByComment(Long commId);
    List<Reaction> findReactionsByUser(String username);
    Reaction createReact(ReactDTO reactDTO);
}
