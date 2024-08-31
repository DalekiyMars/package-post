package com.e_mail.item_post.entity;

import com.e_mail.item_post.common.PackageType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Entity
@Table(name = "departures")
public class Departure {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Enumerated(EnumType.STRING)
    @Column(name = "package_type")
    private PackageType packageType;

    @Column(name = "owner_index")
    @NotEmpty(message = "Index should not be empty")
    @Size(min = 6, max = 6, message = "Your index must be 6 characters")
    private String ownerIndex;

    @Column(name = "owner_address")
    @NotEmpty(message = "Address should not be empty")
    //@Pattern(regexp = Constants.Patterns.ADDRESS_FORMAT)
    private String ownerAddress;

    @Column(name = "owner_name")
    @NotEmpty(message = "Name should not be empty")
    @Size(min = 2, max = 30, message = "Incorrect name format")
    private String ownerName;
}
