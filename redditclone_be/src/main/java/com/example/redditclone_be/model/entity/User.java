package com.example.redditclone_be.model.entity;

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
@Entity
@NoArgsConstructor
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String email;

    @Column
    private String avatar;

    @CreationTimestamp
    @Column(nullable = false)
    private LocalDate regDate;

    @Column
    @Lob
    private String description;

    @Column(nullable = false/*, unique = true */)
    private  String displayName;

    @Column(nullable = false)
    private ERole role;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Moderator> moderators = new HashSet<Moderator>();

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "postedBy")
    private Set<Post> posts = new HashSet<Post>();

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "belongsTo")
    private Set<Comment> comments = new HashSet<Comment>();

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "madeBy")
    private Set<Reaction> reactions = new HashSet<Reaction>();

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "byUser")
    private Set<Report> reports = new HashSet<Report>();

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "isBanned")
    private Set<Banned> banned = new HashSet<Banned>();
}
