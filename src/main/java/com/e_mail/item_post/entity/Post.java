package com.e_mail.item_post.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Post {
    @Id
    @Column(name = "index")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String index;

    @Column(name = "name")
    private String name;

    @Column(name = "owner_address")
    private String ownerAddress;
}
