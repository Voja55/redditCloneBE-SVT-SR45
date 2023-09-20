package com.example.redditclone_be.service;

import com.example.redditclone_be.model.dto.ModeratorDTO;
import com.example.redditclone_be.model.entity.Community;
import com.example.redditclone_be.model.entity.Moderator;
import com.example.redditclone_be.model.entity.User;
import org.springframework.stereotype.Service;

import java.util.List;


public interface ModeratorService {

    Moderator createMod(ModeratorDTO moderatorDTO);

    Moderator findModByUser(Long userId);

    List<Moderator> findModsByCommunity(Long communityId);

    Moderator findModByUser(User user);

    List<Moderator> findModsByCommunity(Community community);

    void deleteModeratorsByCommunity(String community);

}
