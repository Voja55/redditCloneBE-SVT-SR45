package com.example.redditclone_be.model.dto;


import com.example.redditclone_be.model.entity.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class ReactDTO {

    private Long id;
    private EReactionType type;
    private LocalDate timestamp;
    private User madeBy;
    private Post reactingOnPost;
    private Comment reactingOnCom;

    public ReactDTO(Reaction createdReaction){
        this.id = createdReaction.getId();
        this.type = createdReaction.getType();
        this.timestamp = createdReaction.getTimestamp();
        this.madeBy = createdReaction.getMadeBy();
        this.reactingOnPost = createdReaction.getReactingOnPost();
        this.reactingOnCom = createdReaction.getReactingOnCom();

    }
}
