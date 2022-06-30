package com.example.redditclone_be.model.dto;

import com.example.redditclone_be.model.entity.User;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.validation.constraints.NotBlank;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class UserDTO {

    private Long id;
    @NotBlank
    private String username;
    @NotBlank
    private String password;
    @NotBlank
    private String email;

    private String avatar;

    private LocalDate regDate;

    private String description;
    @NotBlank
    private  String displayName;

    public UserDTO(User createdUser){
        this.id = createdUser.getId();
        this.username = createdUser.getUsername();
        this.email = createdUser.getEmail();
        this.regDate = createdUser.getRegDate();
        this.description = createdUser.getDescription();
        this.displayName = createdUser.getDisplayName();
    }
}
