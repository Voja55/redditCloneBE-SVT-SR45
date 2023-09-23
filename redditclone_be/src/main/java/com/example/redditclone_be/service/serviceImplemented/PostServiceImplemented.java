package com.example.redditclone_be.service.serviceImplemented;

import com.example.redditclone_be.lucene.indexing.handlers.PDFHandler;
import com.example.redditclone_be.model.dto.CommunityPdfDTO;
import com.example.redditclone_be.model.dto.PostDTO;
import com.example.redditclone_be.model.dto.PostPdfDTO;
import com.example.redditclone_be.model.dto.ReactDTO;
import com.example.redditclone_be.model.entity.*;
import com.example.redditclone_be.model.entity.elasticEntities.CommunityES;
import com.example.redditclone_be.model.entity.elasticEntities.PostES;
import com.example.redditclone_be.repository.PostESRepository;
import com.example.redditclone_be.repository.PostRepository;
import com.example.redditclone_be.service.CommunityService;
import com.example.redditclone_be.service.PostService;
import com.example.redditclone_be.service.ReactionService;
import com.example.redditclone_be.service.UserService;
import org.elasticsearch.index.query.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.elasticsearch.common.UUIDs.base64UUID;

@Service
public class PostServiceImplemented implements PostService {

    final PostESRepository postRepository;
    final CommunityService communityService;
    final ElasticsearchOperations elasticsearchOperations;
    @Value("${files.path}")
    private String filesPath;

    public PostServiceImplemented(PostESRepository postRepository, CommunityService communityService, ElasticsearchOperations elasticsearchOperations) {
        this.postRepository = postRepository;
        this.communityService = communityService;
        this.elasticsearchOperations = elasticsearchOperations;
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
        newPost.setKarma(1);

        newPost = postRepository.save(newPost);

        return newPost;
    }

    @Override
    public void indexUploadedFilePost(PostPdfDTO postPdfDTO) throws IOException {
        for (MultipartFile file : postPdfDTO.getFiles()) {
            if (file.isEmpty()) {
                continue;
            }
            String fileName = saveUploadedFile(file);
            if (fileName != null){
                PostES postIndexUnit = new PDFHandler().getIndexUnitPost(new File(fileName));
                postIndexUnit.setId(postPdfDTO.getId());
                postIndexUnit.setTitle(postPdfDTO.getTitle());
                postIndexUnit.setText(postPdfDTO.getText());
                postIndexUnit.setCommunity(postPdfDTO.getCommunity());
                postIndexUnit.setPostedBy(postPdfDTO.getPostedBy());
                postIndexUnit.setKarma(postPdfDTO.getKarma());
                postRepository.save(postIndexUnit);
            }
        }
    }

    private String saveUploadedFile(MultipartFile file) throws IOException {
        String retVal = null;
        if (!file.isEmpty()) {
            byte[] bytes = file.getBytes();
            Path path = Paths.get(new File(filesPath).getAbsolutePath() + File.separator + file.getOriginalFilename());
            Files.write(path, bytes);
            retVal = path.toString();
        }
        return retVal;
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

    @Override
    public List<PostES> searchPosts(String comm, String input, Long minKarma, Long maxKarma) {
        MultiMatchQueryBuilder multimatchQuery = QueryBuilders.multiMatchQuery(input, "title", "text", "fileText");
        WildcardQueryBuilder titleWildcard = QueryBuilders.wildcardQuery("title", input);
        WildcardQueryBuilder textWildcard = QueryBuilders.wildcardQuery("text", input);
        WildcardQueryBuilder filetextWildcard = QueryBuilders.wildcardQuery("fileText", input);
        RangeQueryBuilder rangeQuery = QueryBuilders.rangeQuery("karma")
                .gte(minKarma)
                .lte(maxKarma);

        QueryBuilder finalQuery = QueryBuilders.boolQuery()
                .should(multimatchQuery)
                .should(titleWildcard)
                .should(textWildcard)
                .should(filetextWildcard)
                .must(rangeQuery);
        NativeSearchQuery searchQuery = new NativeSearchQueryBuilder().withFilter(finalQuery).build();
        SearchHits<PostES> postHits = elasticsearchOperations.search(searchQuery, PostES.class, IndexCoordinates.of("posts"));
        List<PostES> posts = new ArrayList<>();
        postHits.forEach(hit -> {
            posts.add(hit.getContent());
        });
        return posts;
    }

    @Override
    public boolean editKarma(Reaction reaction) {
        Optional<PostES> existingPost = postRepository.findById(reaction.getReactingOnPost());
        if (existingPost.isPresent()) {
            PostES editPost = existingPost.get();
            int karma = editPost.getKarma();
            if (reaction.getType().equals(EReactionType.UPVOTE)){
                karma = karma + 1;
                editPost.setKarma(karma);
            } else if(reaction.getType().equals(EReactionType.DOWNVOTE)) {
                karma = karma - 1;
                editPost.setKarma(karma);
            } else {
                return false;
            }
            postRepository.save(editPost);
            return true;
        }
        return false;
    }
}
