package com.example.redditclone_be.model.dto;

import com.example.redditclone_be.model.entity.Comment;
import com.example.redditclone_be.model.entity.Post;
import com.example.redditclone_be.model.entity.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class CommentDTO {

    private Long id;
    private String text;
    private LocalDate timestamp;
    private boolean isDeleted;
    private Comment repliesTo;
    private String commentsOn;
    private User belongsTo;

    public CommentDTO(Comment createdComment){
        this.id = createdComment.getId();
        this.text = createdComment.getText();
        this.timestamp = createdComment.getTimestamp();
        this.isDeleted = createdComment.isDeleted();
        this.repliesTo = createdComment.getRepliesTo();
        this.commentsOn = createdComment.getCommentsOn();
        this.belongsTo = createdComment.getBelongsTo();
    }
}
