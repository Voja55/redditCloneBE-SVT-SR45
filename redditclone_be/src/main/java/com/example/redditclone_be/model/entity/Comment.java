package com.example.redditclone_be.model.entity;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    @Column(nullable = false)
    private String text;

    @CreationTimestamp
    @Column(nullable = false)
    private LocalDate timestamp;

    @Column(nullable = false)
    private boolean isDeleted;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "repliesTo")
    private Set<Comment> replies = new HashSet<Comment>();

    @ManyToOne
    private Comment repliesTo;

    @ManyToOne
    private Post commentsOn;

    @ManyToOne
    private User belongsTo;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "comment")
    private Set<Report> reports = new HashSet<Report>();

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "reactingOnCom")
    private Set<Reaction> reactions = new HashSet<Reaction>();
}
