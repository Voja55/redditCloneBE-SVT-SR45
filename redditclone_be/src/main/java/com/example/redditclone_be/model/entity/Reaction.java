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
public class Reaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private EReactionType type;

    @CreationTimestamp
    @Column(nullable = false)
    private LocalDate timestamp;

    @ManyToOne
    private User madeBy;

    @ManyToOne
    private Post reactingOnPost;

    @ManyToOne
    private Comment reactingOnCom;
}
