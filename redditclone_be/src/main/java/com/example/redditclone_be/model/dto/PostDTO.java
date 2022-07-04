package com.example.redditclone_be.model.dto;

import com.example.redditclone_be.model.entity.Community;
import com.example.redditclone_be.model.entity.Flair;
import com.example.redditclone_be.model.entity.Post;
import com.example.redditclone_be.model.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class PostDTO {

    private Long id;
    private String title;
    private String text;
    private LocalDate creationDate;
    private String imgPath;
    private Community community;
    private User postedBy;
    private Flair flair;

    public PostDTO(Post createdPost){
        this.id = createdPost.getId();
        this.title = createdPost.getTitle();
        this.text = createdPost.getText();
        this.creationDate = createdPost.getCreationDate();
        this.imgPath= createdPost.getImgPath();
        this.community = createdPost.getCommunity();
        this.postedBy = createdPost.getPostedBy();
        this.flair = createdPost.getFlair();

    }
}
