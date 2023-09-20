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
public class Banned {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CreationTimestamp
    @Column
    private LocalDate timeStamp;

    @ManyToOne
    private Moderator bannedBy;

    @ManyToOne
    private User isBanned;

    @Column
    private String bannedIn;

}

