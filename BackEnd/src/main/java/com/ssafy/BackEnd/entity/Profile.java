package com.ssafy.BackEnd.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "profile")
@RequiredArgsConstructor
@Getter @Setter
public class Profile {

    @Id
    @GeneratedValue
    @Column(name = "profile_id")
    private Long profile_id;

    private String name;

    private String job;

    private String stack;

    private String email;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "image_id")
    Image image;

    @OneToMany(mappedBy = "profile", cascade = CascadeType.ALL)
    List<UserFeed> user_feed = new ArrayList<>();
}
