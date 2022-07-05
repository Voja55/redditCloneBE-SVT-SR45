package com.example.redditclone_be.model.dto;

import com.example.redditclone_be.model.entity.Community;
import com.example.redditclone_be.model.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
public class ModeratorDTO {

    private Long id;
    private User user;
    private Community community;

}
