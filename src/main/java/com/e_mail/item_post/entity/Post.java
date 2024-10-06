package com.e_mail.item_post.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Data
@ToString
@Entity
@Table(name = "posts")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "index")
    private String index;

    @Column(name = "name")
    private String name;

    @Column(name = "owner_address")
    private String ownerAddress;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "post")
    private List<DeparturePost> postWithThisDeparture = new ArrayList<>();

}
