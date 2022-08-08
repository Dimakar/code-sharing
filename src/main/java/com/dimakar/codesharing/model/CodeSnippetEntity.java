package com.dimakar.codesharing.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@With
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "snippet")
public class CodeSnippetEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "uid")
    private UUID uid;

    @Column(name = "code", length = 10000)
    private String code;
    @Column(name = "date")
    private LocalDateTime date;
    @Column(name = "time")
    private Long time;
    @Column(name = "views")
    private Integer views;
    @Column(name = "hasRestriction")
    private Boolean hasRestriction;
    @Column(name = "hasTimeRestriction")
    private Boolean hasTimeRestriction;
    @Column(name = "hasViewsRestriction")
    private Boolean hasViewsRestriction;
}
