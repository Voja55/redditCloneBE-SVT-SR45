package com.example.redditclone_be.controller;

import com.example.redditclone_be.model.dto.CommentDTO;
import com.example.redditclone_be.model.dto.ReactDTO;
import com.example.redditclone_be.model.entity.*;
import com.example.redditclone_be.model.entity.elasticEntities.PostES;
import com.example.redditclone_be.service.CommentService;
import com.example.redditclone_be.service.PostService;
import com.example.redditclone_be.service.ReactionService;
import com.example.redditclone_be.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("comments")
public class CommentController {

    final CommentService commentService;
    final PostService postService;
    final UserService userService;
    final ReactionService reactionService;

    public CommentController(CommentService commentService, PostService postService, UserService userService, ReactionService reactionService) {
        this.commentService = commentService;
        this.postService = postService;
        this.userService = userService;
        this.reactionService = reactionService;
    }

    @PostMapping("/create/onPost/{postId}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<CommentDTO> createCommentPost(@RequestBody @Validated CommentDTO newCom,
                                                      @PathVariable(value = "postId") String postId, Principal userinfo) {

        User user = userService.findByUsername(userinfo.getName());
        if (user == null) {
            return new ResponseEntity<>(null, HttpStatus.NOT_ACCEPTABLE);
        }
        PostES post = postService.findPostById(postId);
        if (post == null){
            return new ResponseEntity<>(null, HttpStatus.NOT_ACCEPTABLE);
        }
        newCom.setBelongsTo(user);
        newCom.setCommentsOn(post.getId());
        Comment createdCom = commentService.createComment(newCom);
        if (createdCom == null){
            return new ResponseEntity<>(null, HttpStatus.NOT_ACCEPTABLE);
        }
        CommentDTO commentDTO = new CommentDTO(createdCom);

        ReactDTO reactDTO = new ReactDTO();
        reactDTO.setType(EReactionType.UPVOTE);
        reactDTO.setMadeBy(user);
        reactDTO.setReactingOnCom(createdCom);

        Reaction newReact = reactionService.createReact(reactDTO);

        return new ResponseEntity(commentDTO, HttpStatus.OK);
    }

    @PostMapping("/create/onCom/{comId}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<CommentDTO> createCommentCom(@RequestBody @Validated CommentDTO newCom,
                                                      @PathVariable(value = "comId") Long comId, Principal userinfo) {

        User user = userService.findByUsername(userinfo.getName());
        if (user == null) {
            return new ResponseEntity<>(null, HttpStatus.NOT_ACCEPTABLE);
        }
        Comment comment = commentService.findCommentById(comId);
        if (comment == null){
            return new ResponseEntity<>(null, HttpStatus.NOT_ACCEPTABLE);
        }
        newCom.setBelongsTo(user);
        newCom.setRepliesTo(comment);
        Comment createdCom = commentService.createComment(newCom);
        if (createdCom == null){
            return new ResponseEntity<>(null, HttpStatus.NOT_ACCEPTABLE);
        }
        CommentDTO commentDTO = new CommentDTO(createdCom);

        ReactDTO reactDTO = new ReactDTO();
        reactDTO.setType(EReactionType.UPVOTE);
        reactDTO.setMadeBy(user);
        reactDTO.setReactingOnCom(createdCom);

        Reaction newReact = reactionService.createReact(reactDTO);

        return new ResponseEntity(commentDTO, HttpStatus.OK);
    }

    @GetMapping("/post/{postId}")
    public List<Comment> commentsOnPost(@PathVariable(value = "postId") String postId){
        PostES post = postService.findPostById(postId);
        return commentService.findCommentsByPost(post.getId());
    }

    @GetMapping("/comment/{commentId}")
    public List<Comment> commentsOnComment(@PathVariable(value = "commentId") Long commmentId){
        Comment comment = commentService.findCommentById(commmentId);
        return commentService.findCommentsByComment(comment);
    }
}
