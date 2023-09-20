package com.example.redditclone_be.model.dto;


import com.example.redditclone_be.model.entity.Community;
import com.example.redditclone_be.model.entity.elasticEntities.CommunityES;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class CommunityDTO {

    private String id;
    private String name;
    private String description;
    private LocalDate creationDate;
    private boolean isSuspended;
    private String suspendedReason;

    public CommunityDTO(CommunityES createdCommunity){

        this.id = createdCommunity.getId();
        this.name = createdCommunity.getName();
        this.description = createdCommunity.getDescription();
        this.creationDate = createdCommunity.getCreationDate();
        this.isSuspended = createdCommunity.isSuspended();
        this.suspendedReason = createdCommunity.getSuspendedReason();

    }
}
