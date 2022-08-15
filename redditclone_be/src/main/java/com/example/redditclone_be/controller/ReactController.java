package com.example.redditclone_be.controller;

import com.example.redditclone_be.model.dto.ReactDTO;
import com.example.redditclone_be.model.entity.*;
import com.example.redditclone_be.service.CommentService;
import com.example.redditclone_be.service.PostService;
import com.example.redditclone_be.service.ReactionService;
import com.example.redditclone_be.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;

public class ReactController {

    final UserService userService;
    final ReactionService reactionService;
    final PostService postService;
    final CommentService commentService;

    public ReactController(UserService userService, ReactionService reactionService, PostService postService, CommentService commentService) {
        this.userService = userService;
        this.reactionService = reactionService;
        this.postService = postService;
        this.commentService = commentService;
    }


    //TODO: kreiranje reakcija na post
    @PostMapping("/post/{postID}/upvote")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<ReactDTO> upvotePost(@PathVariable(value = "postID") Long postId, Principal userinfo){

        User user = userService.findByUsername(userinfo.getName());
        if (user == null) {
            return new ResponseEntity<>(null, HttpStatus.NOT_ACCEPTABLE);
        }
        Post post = postService.findPostById(postId);
        if (post == null) {
            return new ResponseEntity<>(null, HttpStatus.NOT_ACCEPTABLE);
        }
        ReactDTO reactDTO = new ReactDTO();
        reactDTO.setType(EReactionType.UPVOTE);
        reactDTO.setMadeBy(user);
        reactDTO.setReactingOnPost(post);

        Reaction newReact = reactionService.createReact(reactDTO);

        ReactDTO newReactDTO = new ReactDTO(newReact);

        return new ResponseEntity(newReactDTO, HttpStatus.OK);
    }
    @PostMapping("/post/{postID}/downvote")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<ReactDTO> downvotePost(@PathVariable(value = "postID") Long postId, Principal userinfo){

        User user = userService.findByUsername(userinfo.getName());
        if (user == null) {
            return new ResponseEntity<>(null, HttpStatus.NOT_ACCEPTABLE);
        }
        Post post = postService.findPostById(postId);
        if (post == null) {
            return new ResponseEntity<>(null, HttpStatus.NOT_ACCEPTABLE);
        }
        ReactDTO reactDTO = new ReactDTO();
        reactDTO.setType(EReactionType.DOWNVOTE);
        reactDTO.setMadeBy(user);
        reactDTO.setReactingOnPost(post);

        Reaction newReact = reactionService.createReact(reactDTO);

        ReactDTO newReactDTO = new ReactDTO(newReact);

        return new ResponseEntity(newReactDTO, HttpStatus.OK);
    }

    //TODO: kreiranje reakcija na com
    @PostMapping("/comment/{commentID}/upvote")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<ReactDTO> upvoteCom(@PathVariable(value = "commentID") Long commentId, Principal userinfo){

        User user = userService.findByUsername(userinfo.getName());
        if (user == null) {
            return new ResponseEntity<>(null, HttpStatus.NOT_ACCEPTABLE);
        }
        Comment comment = commentService.findCommentById(commentId);
        if (comment == null) {
            return new ResponseEntity<>(null, HttpStatus.NOT_ACCEPTABLE);
        }
        ReactDTO reactDTO = new ReactDTO();
        reactDTO.setType(EReactionType.UPVOTE);
        reactDTO.setMadeBy(user);
        reactDTO.setReactingOnCom(comment);

        Reaction newReact = reactionService.createReact(reactDTO);

        ReactDTO newReactDTO = new ReactDTO(newReact);

        return new ResponseEntity(newReactDTO, HttpStatus.OK);
    }
    @PostMapping("/comment/{commentID}/downvote")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<ReactDTO> downvoteCom(@PathVariable(value = "commentID") Long commentId, Principal userinfo){

        User user = userService.findByUsername(userinfo.getName());
        if (user == null) {
            return new ResponseEntity<>(null, HttpStatus.NOT_ACCEPTABLE);
        }
        Comment comment = commentService.findCommentById(commentId);
        if (comment == null) {
            return new ResponseEntity<>(null, HttpStatus.NOT_ACCEPTABLE);
        }
        ReactDTO reactDTO = new ReactDTO();
        reactDTO.setType(EReactionType.DOWNVOTE);
        reactDTO.setMadeBy(user);
        reactDTO.setReactingOnCom(comment);

        Reaction newReact = reactionService.createReact(reactDTO);

        ReactDTO newReactDTO = new ReactDTO(newReact);

        return new ResponseEntity(newReactDTO, HttpStatus.OK);
    }
}
