package com.example.redditclone_be.service.serviceImplemented;

import com.example.redditclone_be.model.dto.CommunityDTO;
import com.example.redditclone_be.model.entity.Community;
import com.example.redditclone_be.model.entity.elasticEntities.CommunityES;
import com.example.redditclone_be.repository.CommunityESRepository;
import com.example.redditclone_be.repository.CommunityRepository;
import com.example.redditclone_be.service.CommunityService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static org.elasticsearch.common.UUIDs.base64UUID;

@Service
public class CommunityServiceImplemented implements CommunityService {

    final CommunityESRepository communityRepository;

    public CommunityServiceImplemented(CommunityESRepository communityRepository) {
        this.communityRepository = communityRepository;
    }

    @Override
    public CommunityES createCommunity(CommunityDTO communityDTO) {
        Optional<CommunityES> community = communityRepository.findFirstByName(communityDTO.getName());

        if (community.isPresent()){
            return null;
        }
        CommunityES newCommunity = new CommunityES();
        newCommunity.setId(base64UUID());
        newCommunity.setName(communityDTO.getName());
        newCommunity.setDescription((communityDTO.getDescription()));
        newCommunity.setSuspended(false);

        newCommunity = communityRepository.save(newCommunity);

        return newCommunity;
    }

    @Override
    public List<CommunityES> allCommunities() {
        List<CommunityES> communitiesList = communityRepository.findAll();
        return communitiesList;
    }

    @Override
    public CommunityES findById(Long id) {
        Optional<CommunityES> community = communityRepository.findById(id);
        if(community.isPresent()){
            return community.get();
        }
        return null;
    }

    @Override
    public CommunityES saveComm(CommunityES community) {
        return communityRepository.save(community);
    }

    @Override
    public List<CommunityES> allCommunitiesAvailable() {
        return communityRepository.findAllBySuspendedIsFalse();
    }
}
