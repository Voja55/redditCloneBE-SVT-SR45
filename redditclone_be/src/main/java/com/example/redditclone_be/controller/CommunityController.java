package com.example.redditclone_be.controller;


import com.example.redditclone_be.model.dto.CommunityDTO;
import com.example.redditclone_be.model.entity.Community;
import com.example.redditclone_be.service.CommunityService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("community")
public class CommunityController {

    final CommunityService communityService;

    //final ModeratorService moderatorService;

    public CommunityController(CommunityService communityService) {
        this.communityService = communityService;
    }


    @PostMapping("/create")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<CommunityDTO> createCommunity(@RequestBody @Validated CommunityDTO newComm) {

        Community createdComm = communityService.createCommunity(newComm);

        if (createdComm == null) {
            return new ResponseEntity<>(null, HttpStatus.NOT_ACCEPTABLE);
        }

        //Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        //        User u = (User) auth.getPrincipal();
        //TODO: kreiranje moderatora. Prima trazi usera, zatim prosledjuje usera i createdComm u servis za kreiranje moderatora
        //TODO: baciti pogled na security Club-a u Dusanovom primeru

        CommunityDTO communityDTO = new CommunityDTO(createdComm);

        return new ResponseEntity(communityDTO, HttpStatus.CREATED);
    }
}
