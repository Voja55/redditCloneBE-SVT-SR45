package com.example.redditclone_be.controller;


import com.example.redditclone_be.model.dto.PostDTO;
import com.example.redditclone_be.model.entity.Community;
import com.example.redditclone_be.model.entity.Post;
import com.example.redditclone_be.model.entity.User;
import com.example.redditclone_be.service.CommunityService;
import com.example.redditclone_be.service.PostService;
import com.example.redditclone_be.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.parameters.P;
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

    public PostController(PostService postService, UserService userService, CommunityService communityService) {
        this.postService = postService;
        this.userService = userService;
        this.communityService = communityService;
    }

    @PostMapping("/community/{commID}/create")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<PostDTO> createPost(@RequestBody @Validated PostDTO newPost, @PathVariable(value = "commID") Long commId, Principal userinfo) {

        User user = userService.findByUsername(userinfo.getName());
        if (user == null) {
            return new ResponseEntity<>(null, HttpStatus.NOT_ACCEPTABLE);
        }
        Community community = communityService.findById(commId);
        if (community == null) {
            return new ResponseEntity<>(null, HttpStatus.NOT_ACCEPTABLE);
        }

        newPost.setCommunity(community);
        newPost.setPostedBy(user);
        Post createdPost = postService.createPost(newPost);
        if (createdPost == null) {
            return new ResponseEntity<>(null, HttpStatus.NOT_ACCEPTABLE);
        }
        PostDTO postDTO = new PostDTO(createdPost);

        return new ResponseEntity(postDTO, HttpStatus.CREATED);
    }


    @GetMapping("/community/{commId}/posts")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public @ResponseBody
    List<Post> communityPosts(@PathVariable(value = "commId") Long id){
        return postService.findPostsByCommunity(id);
    }

}
