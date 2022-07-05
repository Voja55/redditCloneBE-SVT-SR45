package com.example.redditclone_be.service.serviceImplemented;

import com.example.redditclone_be.model.dto.ModeratorDTO;
import com.example.redditclone_be.model.entity.Community;
import com.example.redditclone_be.model.entity.Moderator;
import com.example.redditclone_be.model.entity.User;
import com.example.redditclone_be.repository.ModeratorRepository;
import com.example.redditclone_be.service.ModeratorService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ModeratorServiceImplemented implements ModeratorService {

    final ModeratorRepository moderatorRepository;

    public ModeratorServiceImplemented(ModeratorRepository moderatorRepository) {
        this.moderatorRepository = moderatorRepository;
    }

    @Override
    public Moderator createMod(ModeratorDTO moderatorDTO) {

       Moderator newModerator = new Moderator();
       newModerator.setUser(moderatorDTO.getUser());
       newModerator.setCommunity(moderatorDTO.getCommunity());

       newModerator = moderatorRepository.save(newModerator);

        return newModerator;
    }

    @Override
    public Moderator findModByUser(Long userId) {
        return null;
    }

    @Override
    public List<Moderator> findModsByCommunity(Long communityId) {
        return null;
    }

    @Override
    public Moderator findModByUser(User user) {
        return null;
    }

    @Override
    public List<Moderator> findModsByCommunity(Community community) {
        return null;
    }

    @Override
    public void deleteModeratorsByCommunity(Community community) {
        moderatorRepository.deleteAllByCommunity(community);
    }
}
