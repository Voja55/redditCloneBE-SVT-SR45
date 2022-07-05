package com.example.redditclone_be.service;

import com.example.redditclone_be.model.dto.CommunityDTO;
import com.example.redditclone_be.model.entity.Community;

import java.util.List;

public interface CommunityService {

    Community createCommunity(CommunityDTO communityDTO);
    List<Community> allCommunities();
    Community findById(Long id);
    Community saveComm(Community community);
    List<Community> allCommunitiesAvailable();
}
