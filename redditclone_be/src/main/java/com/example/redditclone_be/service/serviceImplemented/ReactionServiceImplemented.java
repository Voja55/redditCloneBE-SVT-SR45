package com.example.redditclone_be.service.serviceImplemented;

import com.example.redditclone_be.model.dto.ReactDTO;
import com.example.redditclone_be.model.entity.Comment;
import com.example.redditclone_be.model.entity.Post;
import com.example.redditclone_be.model.entity.Reaction;
import com.example.redditclone_be.model.entity.User;
import com.example.redditclone_be.model.entity.elasticEntities.PostES;
import com.example.redditclone_be.repository.ReactionRepository;
import com.example.redditclone_be.service.CommentService;
import com.example.redditclone_be.service.PostService;
import com.example.redditclone_be.service.ReactionService;
import com.example.redditclone_be.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReactionServiceImplemented implements ReactionService {

    final ReactionRepository reactionRepository;
    final PostService postService;
    final CommentService comService;
    final UserService userService;

    public ReactionServiceImplemented(ReactionRepository reactionRepository, PostService postService, CommentService comService, UserService userService) {
        this.reactionRepository = reactionRepository;
        this.postService = postService;
        this.comService = comService;
        this.userService = userService;
    }

    @Override
    public List<Reaction> findReactionsByPost(String postId) {
        List<Reaction> reactions = reactionRepository.findAllByReactingOnPost(postId);

        return reactions;
    }

    @Override
    public List<Reaction> findReactionsByComment(Long commId) {
        Comment comment = comService.findCommentById(commId);
        List<Reaction> reactions = reactionRepository.findAllByReactingOnCom(comment);
        return reactions;
    }

    @Override
    public List<Reaction> findReactionsByUser(String username) {
        User user = userService.findByUsername(username);
        List<Reaction> reactions = reactionRepository.findAllByMadeBy(user);
        return reactions;
    }

    @Override
    public Reaction createReact(ReactDTO reactDTO) {

        Reaction newReact = new Reaction();
        newReact.setType(reactDTO.getType());
        newReact.setReactingOnPost(reactDTO.getReactingOnPost());
        newReact.setReactingOnCom(reactDTO.getReactingOnCom());
        newReact.setMadeBy(reactDTO.getMadeBy());

        newReact = reactionRepository.save(newReact);

        return newReact;
    }
}
