package com.example.redditclone_be.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

//@Getter
//@Setter
//@Entity
//@NoArgsConstructor
public class Community {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column
//    private Long id;
//
//    @Column(nullable = false)
//    private String name;
//
//    @Column
//    private String description;
//
//    @CreationTimestamp
//    @Column(nullable = false)
//    private LocalDate creationDate;
//
//    @Column(nullable = false)
//    private boolean isSuspended;
//
//    @Column
//    private String suspendedReason;
//
//    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "community")
//    @JsonIgnore
//    private Set<Post> posts = new HashSet<Post>();
//
//    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "bannedIn")
//    @JsonIgnore
//    private Set<Banned> bans = new HashSet<Banned>();
//
//    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "community")
//    @JsonIgnore
//    private Set<Moderator> moderators = new HashSet<Moderator>();
//
//    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "belongsTo")
//    @JsonIgnore
//    private Set<Rule> rules = new HashSet<Rule>();
//
//    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    @JsonIgnore
//    private Set<Flair> flairs = new HashSet<Flair>();

}
