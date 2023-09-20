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
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping
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


    @GetMapping("/post/{postId}/karma")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public Integer postKarma(@PathVariable(value = "postId") String postId){
        Integer karma = 0;
        List<Reaction> reactions = reactionService.findReactionsByPost(postId);
        for(Reaction reaction : reactions){
            if(reaction.getType().equals(EReactionType.UPVOTE)){
                karma += 1;
            } else if (reaction.getType().equals(EReactionType.DOWNVOTE)) {
                karma -= 1;
            }
        }

        return karma;
    }

    @PostMapping("/post/{postID}/upvote")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<ReactDTO> upvotePost(@PathVariable(value = "postID") String postId, Principal userinfo){

        User user = userService.findByUsername(userinfo.getName());
        if (user == null) {
            return new ResponseEntity<>(null, HttpStatus.NOT_ACCEPTABLE);
        }
//        Post post = postService.findPostById(postId);
//        if (post == null) {
//            return new ResponseEntity<>(null, HttpStatus.NOT_ACCEPTABLE);
//        }
        ReactDTO reactDTO = new ReactDTO();
        reactDTO.setType(EReactionType.UPVOTE);
        reactDTO.setMadeBy(user);
        reactDTO.setReactingOnPost(postId);

        Reaction newReact = reactionService.createReact(reactDTO);

        ReactDTO newReactDTO = new ReactDTO(newReact);

        return new ResponseEntity(newReactDTO, HttpStatus.OK);
    }
    @PostMapping("/post/{postID}/downvote")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<ReactDTO> downvotePost(@PathVariable(value = "postID") String postId, Principal userinfo){

        User user = userService.findByUsername(userinfo.getName());
        if (user == null) {
            return new ResponseEntity<>(null, HttpStatus.NOT_ACCEPTABLE);
        }
//        Post post = postService.findPostById(postId);
//        if (post == null) {
//            return new ResponseEntity<>(null, HttpStatus.NOT_ACCEPTABLE);
//        }
        ReactDTO reactDTO = new ReactDTO();
        reactDTO.setType(EReactionType.DOWNVOTE);
        reactDTO.setMadeBy(user);
        reactDTO.setReactingOnPost(postId);

        Reaction newReact = reactionService.createReact(reactDTO);

        ReactDTO newReactDTO = new ReactDTO(newReact);

        return new ResponseEntity(newReactDTO, HttpStatus.OK);
    }

    @GetMapping("/comment/{comId}/karma")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public Integer comKarma(@PathVariable(value = "comId") Long comId){
        Integer karma = 0;
        List<Reaction> reactions = reactionService.findReactionsByComment(comId);
        for(Reaction reaction : reactions){
            if(reaction.getType().equals(EReactionType.UPVOTE)){
                karma += 1;
            } else if (reaction.getType().equals(EReactionType.DOWNVOTE)) {
                karma -= 1;
            }
        }

        return karma;
    }

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
