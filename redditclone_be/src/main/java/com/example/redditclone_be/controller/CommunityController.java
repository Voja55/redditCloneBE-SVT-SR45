package com.example.redditclone_be.controller;


import com.example.redditclone_be.model.dto.CommunityDTO;
import com.example.redditclone_be.model.dto.CommunityPdfDTO;
import com.example.redditclone_be.model.dto.ModeratorDTO;
import com.example.redditclone_be.model.dto.SuspendDTO;
import com.example.redditclone_be.model.entity.Community;
import com.example.redditclone_be.model.entity.User;
import com.example.redditclone_be.model.entity.elasticEntities.CommunityES;
import com.example.redditclone_be.service.CommunityService;
import com.example.redditclone_be.service.ModeratorService;
import com.example.redditclone_be.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("community")
public class CommunityController {

    final CommunityService communityService;
    final ModeratorService moderatorService;
    final UserService userService;
    final ModelMapper modelMapper;

    public CommunityController(CommunityService communityService, ModeratorService moderatorService, UserService userService, ModelMapper modelMapper) {
        this.communityService = communityService;
        this.moderatorService = moderatorService;
        this.userService = userService;
        this.modelMapper = modelMapper;
    }


    @PostMapping("/create")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<CommunityDTO> createCommunity(@RequestBody @Validated CommunityDTO newComm, Principal userinfo) {

        CommunityES createdComm = communityService.createCommunity(newComm);
        if (createdComm == null) {
            return new ResponseEntity<>(null, HttpStatus.NOT_ACCEPTABLE);
        }
        User user = userService.findByUsername(userinfo.getName());
        if (user == null) {
            return new ResponseEntity<>(null, HttpStatus.NOT_ACCEPTABLE);
        }

        ModeratorDTO moderatorDTO = new ModeratorDTO();
        moderatorDTO.setCommunity(createdComm.getId());
        moderatorDTO.setUser(user);
        moderatorService.createMod(moderatorDTO);

        CommunityDTO communityDTO = new CommunityDTO(createdComm);

        return new ResponseEntity(communityDTO, HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping(path = "/createPdf", consumes = {"multipart/form-data"})
    public ResponseEntity<CommunityDTO> createCommunityPDF(@ModelAttribute CommunityPdfDTO newComm, Principal userinfo) throws IOException {

        if (newComm.getName() == null || newComm.getDescription() == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        CommunityDTO communityDTO = new CommunityDTO();
        communityDTO.setName(newComm.getName());
        communityDTO.setDescription(newComm.getDescription());
        CommunityES communityES = communityService.createCommunity(communityDTO);

        newComm.setId(communityES.getId());
        newComm.setSuspended(communityES.isSuspended());
        newComm.setPostsNum(communityES.getPostsNum());
        newComm.setCreationDate(communityES.getCreationDate());

        communityService.indexUploadedFileCommunity(newComm);

        User user = userService.findByUsername(userinfo.getName());
        if (user == null) {
            return new ResponseEntity<>(null, HttpStatus.NOT_ACCEPTABLE);
        }

        ModeratorDTO moderatorDTO = new ModeratorDTO();
        moderatorDTO.setCommunity(newComm.getId());
        moderatorDTO.setUser(user);
        moderatorService.createMod(moderatorDTO);

        return new ResponseEntity(new CommunityDTO(communityES), HttpStatus.CREATED);
    }

    @GetMapping("/all-available")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public List<CommunityES> communityListAvailable(){
        return communityService.allCommunitiesAvailable();
    }

    @GetMapping("/all")
    @PreAuthorize("hasRole('ADMIN')")
    public List<CommunityES> communityList(){
        return communityService.allCommunities();
    }

    @GetMapping("/{commID}")
    public CommunityDTO community(@PathVariable(value = "commID") Long id){
        return modelMapper.map(communityService.findById(id), CommunityDTO.class);
    }

    @GetMapping("/search")
    public List<CommunityES> communitySearchTitle(@PathVariable(value = "input")String input, @PathVariable(value = "from") long from, @PathVariable(value = "to") long to){
        return communityService.communitySearch(input, from, to);
    }

    @PutMapping("/{commID}/suspend")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> suspendCommunity(@PathVariable(value = "commID") Long id, @RequestBody @Validated SuspendDTO suspendDTO) {
        CommunityES suspended = communityService.findById(id);
        if (suspended == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        suspended.setSuspended(true);
        suspended.setSuspendedReason(suspendDTO.getSuspendedReason());
        suspended = communityService.saveComm(suspended);

        //moderatorService.deleteModeratorsByCommunity(suspended);

        return new ResponseEntity(null, HttpStatus.OK);
    }

    //TODO: preko moderatora vratiti listu svojih community-ja (Takodje uz to Moderator Service dovrsiti)

}
