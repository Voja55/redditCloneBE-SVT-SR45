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

//@Entity
//@Getter
//@Setter
//@NoArgsConstructor
public class Post{

//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @Column
//    private String title;
//
//    @Column
//    private String text;
//
//    @CreationTimestamp
//    @Column
//    private LocalDate creationDate;
//
//    @Column
//    private String imgPath;
//
//    @ManyToOne
//    private Community community;
//
//    @ManyToOne
//    private User postedBy;
//
//    @ManyToOne
//    private Flair flair;
//
//    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "commentsOn")
//    @JsonIgnore
//    private Set<Comment> comments = new HashSet<Comment>();
//
//    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "reactingOnPost")
//    @JsonIgnore
//    private Set<Reaction> reactions = new HashSet<Reaction>();
//
//    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "post")
//    @JsonIgnore
//    private Set<Report> reports = new HashSet<Report>();
}

