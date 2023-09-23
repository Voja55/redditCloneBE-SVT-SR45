package com.example.redditclone_be.service;

import com.example.redditclone_be.model.dto.CommunityDTO;
import com.example.redditclone_be.model.dto.CommunityPdfDTO;
import com.example.redditclone_be.model.entity.Community;
import com.example.redditclone_be.model.entity.Reaction;
import com.example.redditclone_be.model.entity.elasticEntities.CommunityES;
import com.example.redditclone_be.model.entity.elasticEntities.PostES;

import java.io.IOException;
import java.util.List;

public interface CommunityService {

    CommunityES createCommunity(CommunityDTO communityDTO);
    List<CommunityES> allCommunities();
    CommunityES findById(Long id);
    CommunityES saveComm(CommunityES community);
    List<CommunityES> allCommunitiesAvailable();
    List<CommunityES> communitySearch(String input, long from, long to);
    boolean editPostNum(PostES postES);

    void indexUploadedFileCommunity(CommunityPdfDTO communityPdfDTO) throws IOException;
}
