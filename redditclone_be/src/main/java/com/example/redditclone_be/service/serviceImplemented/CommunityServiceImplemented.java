package com.example.redditclone_be.service.serviceImplemented;

import com.example.redditclone_be.model.dto.CommunityDTO;
import com.example.redditclone_be.model.entity.Community;
import com.example.redditclone_be.repository.CommunityRepository;
import com.example.redditclone_be.service.CommunityService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CommunityServiceImplemented implements CommunityService {

    final CommunityRepository communityRepository;

    public CommunityServiceImplemented(CommunityRepository communityRepository) {
        this.communityRepository = communityRepository;
    }

    @Override
    public Community createCommunity(CommunityDTO communityDTO) {
        Optional<Community> community = communityRepository.findFirstByName(communityDTO.getName());

        if (community.isPresent()){
            return null;
        }
        Community newCommunity = new Community();
        newCommunity.setName(communityDTO.getName());
        newCommunity.setDescription((communityDTO.getDescription()));
        newCommunity.setSuspended(false);

        newCommunity = communityRepository.save(newCommunity);

        return newCommunity;
    }

    @Override
    public List<Community> allCommunities() {
        List<Community> communitiesList = communityRepository.findAll();
        return communitiesList;
    }

    @Override
    public Community findById(Long id) {
        Optional<Community> community = communityRepository.findById(id);
        if(community.isPresent()){
            return community.get();
        }
        return null;
    }

    @Override
    public Community saveComm(Community community) {
        return communityRepository.save(community);
    }

    @Override
    public List<Community> allCommunitiesAvailable() {
        return communityRepository.findAllAvailable();
    }
}
