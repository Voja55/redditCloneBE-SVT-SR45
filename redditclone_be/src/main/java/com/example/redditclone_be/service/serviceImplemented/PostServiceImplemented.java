package com.example.redditclone_be.service.serviceImplemented;

import com.example.redditclone_be.model.dto.PostDTO;
import com.example.redditclone_be.model.entity.Community;
import com.example.redditclone_be.model.entity.Post;
import com.example.redditclone_be.repository.PostRepository;
import com.example.redditclone_be.service.CommunityService;
import com.example.redditclone_be.service.PostService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PostServiceImplemented implements PostService {

    final PostRepository postRepository;
    final CommunityService communityService;

    public PostServiceImplemented(PostRepository postRepository, CommunityService communityService) {
        this.postRepository = postRepository;
        this.communityService = communityService;
    }

    @Override
    public Post createPost(PostDTO postDTO) {

//        Optional<Post> post = postRepository.findById(postDTO.getId());
//        if(post.isPresent()){
//            return null;
//        }
        Post newPost = new Post();
        newPost.setTitle(postDTO.getTitle());
        newPost.setText(postDTO.getText());
        newPost.setCommunity(postDTO.getCommunity());
        newPost.setPostedBy(postDTO.getPostedBy());

        newPost = postRepository.save(newPost);

        return newPost;
    }

    @Override
    public List<Post> findPostsByCommunity(Long commId) {
        Community community = communityService.findById(commId);
        List<Post> postsList =  postRepository.findAllByCommunity(community);

        return postsList;
    }

    @Override
    public List<Post> findPostsByUser(Long userId) {
        return null;
    }
}
