package com.example.redditclone_be.model.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PassChangeDTO {

    private String oldPass;
    private String newPass1;
    private String newPass2;

}
