package com.example.redditclone_be.model.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@Setter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private  String email;

    @Column(nullable = false)
    private String avatar;

    @Column(nullable = false)
    private LocalDate regDate;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false, unique = true)
    private  String displayName;
}
