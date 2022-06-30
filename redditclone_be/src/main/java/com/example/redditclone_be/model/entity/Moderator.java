package com.example.redditclone_be.model.entity;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
//@ToString
@Table(name = "moderator")
public class Moderator {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "moderator_id", unique = true, nullable = false)
//    private Integer moderator_id;
//
//    //User who has Moderator Role
//    @ManyToOne(fetch = FetchType.EAGER)
//    @JoinColumn(name = "user_id", nullable = false)
//    private User user;
//
//    //Communities which he is Moderating
//    @ManyToOne(fetch = FetchType.EAGER)
//    @JoinColumn(name = "community_id", nullable = false)
//    private Community community;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User user;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "bannedBy")
    private Set<Banned> bans = new HashSet<Banned>();

    @ManyToOne
    private Community community;
}
