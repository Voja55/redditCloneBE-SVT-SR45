package com.example.redditclone_be.repository;

import com.example.redditclone_be.model.entity.Community;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CommunityRepository extends JpaRepository<Community, Long> {

    public Optional<Community> findFirstByName(String name);
}
