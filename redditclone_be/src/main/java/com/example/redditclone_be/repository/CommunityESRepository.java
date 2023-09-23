package com.example.redditclone_be.repository;

import com.example.redditclone_be.model.entity.elasticEntities.CommunityES;
import org.apache.poi.sl.draw.geom.GuideIf;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommunityESRepository extends ElasticsearchRepository<CommunityES, String> {

    List<CommunityES> findAll();
    Optional<CommunityES> findFirstByName(String name);
    Optional<CommunityES> findById(Long id);

    List<CommunityES> findAllBySuspendedIsFalse();
}
