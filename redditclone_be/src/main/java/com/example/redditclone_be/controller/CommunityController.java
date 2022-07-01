package com.example.redditclone_be.controller;


import com.example.redditclone_be.model.entity.Post;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CommunityController {

//    @GetMapping("community/{commId}/posts")
//    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
//    public @ResponseBody List<Post> communityPosts(@PathVariable(value = "commId") Long id){
//        return ;
//    }
}
