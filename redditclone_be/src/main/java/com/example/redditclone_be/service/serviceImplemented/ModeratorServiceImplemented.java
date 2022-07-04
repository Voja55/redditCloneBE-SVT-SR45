package com.example.redditclone_be.service.serviceImplemented;

import com.example.redditclone_be.model.dto.ModeratorDTO;
import com.example.redditclone_be.model.entity.Community;
import com.example.redditclone_be.model.entity.Moderator;
import com.example.redditclone_be.model.entity.User;
import com.example.redditclone_be.service.ModeratorService;

import java.util.List;

public class ModeratorServiceImplemented implements ModeratorService {
    @Override
    public Moderator createMod(ModeratorDTO moderatorDTO) {
        return null;
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
}
