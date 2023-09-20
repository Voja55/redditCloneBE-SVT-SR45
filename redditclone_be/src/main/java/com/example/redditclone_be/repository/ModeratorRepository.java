package com.example.redditclone_be.repository;

import com.example.redditclone_be.model.entity.Community;
import com.example.redditclone_be.model.entity.Moderator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ModeratorRepository extends JpaRepository<Moderator, Long> {

    public void deleteAllByCommunityId(String community);
}
