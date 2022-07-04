package com.example.redditclone_be.controller;


import com.example.redditclone_be.model.dto.JwtAuthenticationRequest;
import com.example.redditclone_be.model.dto.UserDTO;
import com.example.redditclone_be.model.dto.UserTokenState;
import com.example.redditclone_be.model.entity.User;
import com.example.redditclone_be.security.TokenUtils;
import com.example.redditclone_be.service.UserService;
import lombok.AllArgsConstructor;
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
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("users")
public class UserController {

    final UserService userService;

    @Autowired
    TokenUtils tokenUtils;

    @Autowired
    AuthenticationManager authenticationManager;

    final ModelMapper modelMapper;

    public UserController(UserService userService, ModelMapper modelMapper) {
        this.userService = userService;
        this.modelMapper = modelMapper;
    }


    @PostMapping("/registration")
    public ResponseEntity<UserDTO> registration(@RequestBody @Validated UserDTO newUser){

        User createdUser = userService.createUser(newUser);

        if(createdUser == null){
            return new ResponseEntity<>(null, HttpStatus.NOT_ACCEPTABLE);
        }
        UserDTO userDTO = new UserDTO(createdUser);

        return new ResponseEntity(userDTO, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    @CrossOrigin
    public ResponseEntity<UserTokenState> createAuthenticationToken(@RequestBody JwtAuthenticationRequest authenticationRequest, HttpServletResponse response) {

        // Ukoliko kredencijali nisu ispravni, logovanje nece biti uspesno, desice se
        // AuthenticationException
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                authenticationRequest.getUsername(), authenticationRequest.getPassword()));

        // Ukoliko je autentifikacija uspesna, ubaci korisnika u trenutni security
        // kontekst
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Kreiraj token za tog korisnika
        UserDetails user = (UserDetails) authentication.getPrincipal();
        String jwt = tokenUtils.generateToken(user);
        int expiresIn = tokenUtils.getExpiredIn();

        // Vrati token kao odgovor na uspesnu autentifikaciju
        return ResponseEntity.ok(new UserTokenState(jwt, expiresIn));
    }

    @GetMapping("/all")
    @PreAuthorize("hasRole('ADMIN')")
    public List<User> usersList(){
        return userService.getAllUsers();
    }
//
//    @GetMapping("/myprofile")
//    //@PreAuthorize("hasRole('ROLE_USER')")
//    @PreAuthorize("hasRole('ROLE_USER')")
//    private User user (Principal user) {
//        return userService.findByUsername(user.getName());
//    }

    @GetMapping("/profile")
    @CrossOrigin
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public UserDTO user(Principal user) {
        return modelMapper.map(userService.findByUsername(user.getName()), UserDTO.class);
    }

    public void Logout(){

    }


}
