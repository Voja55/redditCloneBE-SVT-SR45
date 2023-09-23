package com.example.redditclone_be.model.dto;

import com.example.redditclone_be.model.entity.elasticEntities.CommunityES;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
@Getter
@Setter
@NoArgsConstructor
public class CommunityPdfDTO {

    private String id;
    private String name;
    private String description;
    private LocalDate creationDate;
    private boolean isSuspended;
    private String suspendedReason;
    private Integer postsNum;
    private MultipartFile[] files;

    public CommunityPdfDTO(CommunityES createdCommunity){

        this.id = createdCommunity.getId();
        this.name = createdCommunity.getName();
        this.description = createdCommunity.getDescription();
        this.creationDate = createdCommunity.getCreationDate();
        this.isSuspended = createdCommunity.isSuspended();
        this.suspendedReason = createdCommunity.getSuspendedReason();
        this.postsNum = createdCommunity.getPostsNum();

    }
}
