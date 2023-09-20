package com.example.redditclone_be.service.serviceImplemented;

import com.example.redditclone_be.model.dto.PostDTO;
import com.example.redditclone_be.model.dto.ReactDTO;
import com.example.redditclone_be.model.entity.Community;
import com.example.redditclone_be.model.entity.EReactionType;
import com.example.redditclone_be.model.entity.Post;
import com.example.redditclone_be.model.entity.User;
import com.example.redditclone_be.model.entity.elasticEntities.PostES;
import com.example.redditclone_be.repository.PostESRepository;
import com.example.redditclone_be.repository.PostRepository;
import com.example.redditclone_be.service.CommunityService;
import com.example.redditclone_be.service.PostService;
import com.example.redditclone_be.service.ReactionService;
import com.example.redditclone_be.service.UserService;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.elasticsearch.common.UUIDs.base64UUID;

@Service
public class PostServiceImplemented implements PostService {

    final PostESRepository postRepository;
    final CommunityService communityService;

    public PostServiceImplemented(PostESRepository postRepository, CommunityService communityService) {
        this.postRepository = postRepository;
        this.communityService = communityService;
    }

    @Override
    public PostES createPost(PostDTO postDTO) {

//        Optional<Post> post = postRepository.findById(postDTO.getId());
//        if(post.isPresent()){
//            return null;
//        }
        PostES newPost = new PostES();
        newPost.setId(base64UUID());
        newPost.setTitle(postDTO.getTitle());
        newPost.setText(postDTO.getText());
        newPost.setCommunity(postDTO.getCommunity());
        newPost.setPostedBy(postDTO.getPostedBy());

        newPost = postRepository.save(newPost);

        return newPost;
    }

    @Override
    public List<PostES> findPostsByCommunity(String commId) {
        //Community community = communityService.findById(commId);
        List<PostES> postsList =  postRepository.findAllByCommunity(commId);

        return postsList;
    }

    @Override
    public List<PostES> findPostsByUser(String user) {
        return postRepository.findAllByPostedBy(user);
    }

    @Override
    public List<PostES> findAllHome() {
        List<PostES> posts = postRepository.findAll();
        Collections.shuffle(posts);
        return posts;
    }

    @Override
    public PostES findPostById(String postId) {
        Optional<PostES> post = postRepository.findFirstById(postId);
        if (post.isPresent()){
            return post.get();
        }
        return null;
    }
}
