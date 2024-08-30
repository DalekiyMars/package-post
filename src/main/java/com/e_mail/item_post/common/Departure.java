package com.e_mail.item_post.common;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Departure {
    @Id
    @Column(name = "id")
    private int id;

   // @Enumerated(EnumType.STRING)
    @Column(name = "package_type")
    private String package_type;

    @Column(name = "owner_index")
    private String owner_index;

    @Column(name = "owner_address")
    private String owner_address;

    @Column(name = "owner_name")
    private String owner_name;
}
