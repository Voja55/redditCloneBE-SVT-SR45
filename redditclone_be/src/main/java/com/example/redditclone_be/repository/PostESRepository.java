package com.example.redditclone_be.repository;

import com.example.redditclone_be.model.entity.Community;
import com.example.redditclone_be.model.entity.Post;
import com.example.redditclone_be.model.entity.User;
import com.example.redditclone_be.model.entity.elasticEntities.PostES;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.relational.core.sql.In;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostESRepository extends ElasticsearchRepository<PostES, String> {
    List<PostES> findAll();

    List<PostES> findAllByCommunity(String communityId);
    List<PostES> findAllByPostedBy(String userId);
    Optional<PostES> findFirstById(String id);
}
