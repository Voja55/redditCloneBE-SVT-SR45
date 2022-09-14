package com.example.redditclone_be.controller;


import com.example.redditclone_be.model.dto.JwtAuthenticationRequest;
import com.example.redditclone_be.model.dto.PassChangeDTO;
import com.example.redditclone_be.model.dto.UserDTO;
import com.example.redditclone_be.model.dto.UserTokenState;
import com.example.redditclone_be.model.entity.EReactionType;
import com.example.redditclone_be.model.entity.Reaction;
import com.example.redditclone_be.model.entity.User;
import com.example.redditclone_be.security.TokenUtils;
import com.example.redditclone_be.service.ReactionService;
import com.example.redditclone_be.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("users")
public class UserController {

    final UserService userService;

    final ReactionService reactionService;

    @Autowired
    TokenUtils tokenUtils;

    @Autowired
    AuthenticationManager authenticationManager;

    final PasswordEncoder passwordEncoder;

    final ModelMapper modelMapper;

    public UserController(UserService userService, ReactionService reactionService, PasswordEncoder passwordEncoder, ModelMapper modelMapper) {
        this.userService = userService;
        this.reactionService = reactionService;
        this.passwordEncoder = passwordEncoder;
        this.modelMapper = modelMapper;
    }

    @PostMapping("/registration")
    public ResponseEntity<UserDTO> registration(@RequestBody @Validated UserDTO newUser) {

        User createdUser = userService.createUser(newUser);

        if (createdUser == null) {
            return new ResponseEntity<>(null, HttpStatus.NOT_ACCEPTABLE);
        }
        UserDTO userDTO = new UserDTO(createdUser);

        return new ResponseEntity(userDTO, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    @CrossOrigin
    public ResponseEntity<UserTokenState> createAuthenticationToken(@RequestBody JwtAuthenticationRequest authenticationRequest, HttpServletResponse response) {

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                authenticationRequest.getUsername(), authenticationRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserDetails user = (UserDetails) authentication.getPrincipal();
        String jwt = tokenUtils.generateToken(user);
        int expiresIn = tokenUtils.getExpiredIn();

        return ResponseEntity.ok(new UserTokenState(jwt, expiresIn));
    }

    @GetMapping("/all")
    @PreAuthorize("hasRole('ADMIN')")
    public List<User> usersList() {
        return userService.getAllUsers();
    }

    @GetMapping("/profile")
    @CrossOrigin
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public UserDTO user(Principal user) {
        return modelMapper.map(userService.findByUsername(user.getName()), UserDTO.class);
    }

    @GetMapping("/mykarma")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public Integer userKarma(Principal user) {
        Integer karma = 0;
        List<Reaction> reactions = reactionService.findReactionsByUser(user.getName());
        for(Reaction reaction : reactions){
            if(reaction.getType().equals(EReactionType.UPVOTE)){
                karma += 1;
            } else if (reaction.getType().equals(EReactionType.DOWNVOTE)) {
                karma -= 1;
            }
        }
        return karma;
    }

    @PutMapping(value = "/change-password", consumes = "application/json")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<?> ChangePassword(@RequestBody @Validated PassChangeDTO newPass, Principal user) {

        User newpassUser = userService.findByUsername(user.getName());
        if (newpassUser == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        if (!newPass.getNewPass1().equals(newPass.getNewPass2())) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        //TODO: enkodovanje sifre i poredjenje

        newpassUser.setPassword(passwordEncoder.encode(newPass.getNewPass1()));
        newpassUser = userService.saveUser(newpassUser);

        return new ResponseEntity(null, HttpStatus.ACCEPTED);
    }

    @PutMapping(value = "/edit", consumes = "application/json")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<UserDTO> updateUser(@RequestBody @Validated UserDTO userDTO, Principal user) {

        User editedUser = userService.findByUsername(user.getName());

        if (editedUser == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        editedUser.setDisplayName(userDTO.getDisplayName());
        editedUser.setDescription(userDTO.getDescription());

        editedUser = userService.saveUser(editedUser);

        return new ResponseEntity<>(new UserDTO(editedUser), HttpStatus.OK);
    }

}
