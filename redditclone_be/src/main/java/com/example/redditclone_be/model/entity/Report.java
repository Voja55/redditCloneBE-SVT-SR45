package com.example.redditclone_be.model.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class Report {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column
    private EReportReason reason;

    @CreationTimestamp
    @Column(nullable = false)
    private LocalDate timestamp;

    @ManyToOne
    private User byUser;

    @Column(nullable = false)
    private Boolean accepted;

    @ManyToOne
    private Comment comment;

    @ManyToOne
    private Post post;


}
