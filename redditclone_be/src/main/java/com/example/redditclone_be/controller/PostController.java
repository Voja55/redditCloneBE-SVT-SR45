package com.example.redditclone_be.controller;


import com.example.redditclone_be.model.dto.*;
import com.example.redditclone_be.model.entity.*;
import com.example.redditclone_be.model.entity.elasticEntities.CommunityES;
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

import java.io.IOException;
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
        reactDTO.setReactingOnPost(createdPost.getId());

        Reaction newReact = reactionService.createReact(reactDTO);
        //communityService.editPostNum(createdPost);

        ReactDTO newReactDTO = new ReactDTO(newReact);

        return new ResponseEntity(postDTO, HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping(path = "/community/{commID}/createPdf", consumes = {"multipart/form-data"})
    public ResponseEntity<PostDTO> createPostPDF(@ModelAttribute PostPdfDTO newPost,
                                                 @PathVariable(value = "commID") String commId, Principal userinfo) throws IOException {

        if (newPost.getTitle() == null || newPost.getText() == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        User user = userService.findByUsername(userinfo.getName());
        if (user == null) {
            return new ResponseEntity<>(null, HttpStatus.NOT_ACCEPTABLE);
        }

        PostDTO postDTO = new PostDTO();
        postDTO.setCommunity(commId);
        postDTO.setTitle(newPost.getTitle());
        postDTO.setText(newPost.getText());
        postDTO.setPostedBy(user.getId().toString());
        PostES postES = postService.createPost(postDTO);

        newPost.setId(postES.getId());
        newPost.setTitle(postES.getTitle());
        newPost.setText(postES.getText());
        newPost.setCommunity(postES.getCommunity());
        newPost.setPostedBy(postES.getPostedBy());
        newPost.setKarma(postES.getKarma());
        postService.indexUploadedFilePost(newPost);

        ReactDTO reactDTO = new ReactDTO();
        reactDTO.setType(EReactionType.UPVOTE);
        reactDTO.setMadeBy(user);
        reactDTO.setReactingOnPost(newPost.getId());
        reactionService.createReact(reactDTO);

        return new ResponseEntity(new PostDTO(postES), HttpStatus.CREATED);
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
