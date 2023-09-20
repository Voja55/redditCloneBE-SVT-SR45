package com.example.redditclone_be.controller;


import com.example.redditclone_be.model.dto.PostDTO;
import com.example.redditclone_be.model.dto.ReactDTO;
import com.example.redditclone_be.model.entity.*;
import com.example.redditclone_be.model.entity.elasticEntities.PostES;
import com.example.redditclone_be.service.CommunityService;
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
@RequestMapping
public class PostController {

    final PostService postService;
    final UserService userService;
    final CommunityService communityService;
    final ReactionService reactionService;

    public PostController(PostService postService, UserService userService, CommunityService communityService, ReactionService reactionService) {
        this.postService = postService;
        this.userService = userService;
        this.communityService = communityService;
        this.reactionService = reactionService;
    }

    @PostMapping("/community/{commID}/create")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<PostDTO> createPost(@RequestBody @Validated PostDTO newPost, @PathVariable(value = "commID") String commId, Principal userinfo) {

        User user = userService.findByUsername(userinfo.getName());
        if (user == null) {
            return new ResponseEntity<>(null, HttpStatus.NOT_ACCEPTABLE);
        }
//        Community community = communityService.findById(commId);
//        if (community == null) {
//            return new ResponseEntity<>(null, HttpStatus.NOT_ACCEPTABLE);
//        }

        newPost.setCommunity(commId);
        newPost.setPostedBy(user.getId().toString());
        PostES createdPost = postService.createPost(newPost);
        if (createdPost == null) {
            return new ResponseEntity<>(null, HttpStatus.NOT_ACCEPTABLE);
        }
        PostDTO postDTO = new PostDTO(createdPost);

        ReactDTO reactDTO = new ReactDTO();
        reactDTO.setType(EReactionType.UPVOTE);
        reactDTO.setMadeBy(user);
        reactDTO.setReactingOnPost(createdPost.getId().toString());

        Reaction newReact = reactionService.createReact(reactDTO);

        ReactDTO newReactDTO = new ReactDTO(newReact);

        return new ResponseEntity(postDTO, HttpStatus.CREATED);
    }


    @GetMapping("/community/{commId}/posts")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public List<PostES> communityPosts(@PathVariable(value = "commId") String id){
        return postService.findPostsByCommunity(id);
    }

    @GetMapping("/userPosts")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public List<PostES> userPosts(Principal userInfo){
        User user = userService.findByUsername(userInfo.getName());
        return postService.findPostsByUser(user.getId().toString());
    }
    @GetMapping("/home")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public List<PostES> homePage(){
        return postService.findAllHome();
    }

    @GetMapping("/post/{postId}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public PostES getPost(@PathVariable(value="postId") String id){
        return postService.findPostById(id);
    }


}
