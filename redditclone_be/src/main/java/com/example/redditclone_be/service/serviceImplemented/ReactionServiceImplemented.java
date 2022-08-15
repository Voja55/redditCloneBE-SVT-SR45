package com.example.redditclone_be.service.serviceImplemented;

import com.example.redditclone_be.model.dto.ReactDTO;
import com.example.redditclone_be.model.entity.Reaction;
import com.example.redditclone_be.repository.ReactionRepository;
import com.example.redditclone_be.service.ReactionService;
import org.springframework.stereotype.Service;

@Service
public class ReactionServiceImplemented implements ReactionService {

    final ReactionRepository reactionRepository;

    public ReactionServiceImplemented(ReactionRepository reactionRepository) {
        this.reactionRepository = reactionRepository;
    }

    @Override
    public Reaction createReact(ReactDTO reactDTO) {

        Reaction newReact = new Reaction();
        newReact.setType(reactDTO.getType());
        newReact.setReactingOnPost(reactDTO.getReactingOnPost());
        newReact.setReactingOnCom(reactDTO.getReactingOnCom());
        newReact.setMadeBy(reactDTO.getMadeBy());

        newReact = reactionRepository.save(newReact);

        return newReact;
    }
}
