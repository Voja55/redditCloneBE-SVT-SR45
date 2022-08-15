package com.example.redditclone_be.service;

import com.example.redditclone_be.model.dto.ReactDTO;
import com.example.redditclone_be.model.entity.Reaction;

public interface ReactionService {

    Reaction createReact(ReactDTO reactDTO);
}
