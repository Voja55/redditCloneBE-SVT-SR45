package com.example.redditclone_be.service;

import com.example.redditclone_be.model.dto.CommunityDTO;
import com.example.redditclone_be.model.entity.Community;
import com.example.redditclone_be.model.entity.elasticEntities.CommunityES;

import java.util.List;

public interface CommunityService {

    CommunityES createCommunity(CommunityDTO communityDTO);
    List<CommunityES> allCommunities();
    CommunityES findById(Long id);
    CommunityES saveComm(CommunityES community);
    List<CommunityES> allCommunitiesAvailable();
}
