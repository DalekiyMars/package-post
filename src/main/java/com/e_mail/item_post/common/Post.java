package com.e_mail.item_post.common;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class Post {
    @Id
    @Column(name = "index")
    private String index;

    @Column(name = "name")
    private String name;

    @Column(name = "owner_address")
    private String owner_address;
}
