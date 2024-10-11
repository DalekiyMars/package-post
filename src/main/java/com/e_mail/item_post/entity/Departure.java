package com.e_mail.item_post.entity;

import com.e_mail.item_post.common.PackageType;
import com.e_mail.item_post.common.Status;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.hibernate.annotations.UuidGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@ToString
@Entity
@Table(name = "departures")
@Accessors(chain = true)
public class Departure {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.UUID)
    @UuidGenerator
    private UUID id;

    @Enumerated(EnumType.STRING)
    @Column(name = "package_type")
    private PackageType packageType;

    @Column(name = "owner_index")
    @Size(min = 6, max = 6, message = "Your index must be 6 characters")
    private String ownerIndex;

    @Column(name = "owner_address")
    //@Pattern(regexp = Constants.Patterns.ADDRESS_FORMAT)
    private String ownerAddress;

    @Column(name = "owner_name")
    @Size(min = 2, max = 30, message = "Incorrect name format")
    private String ownerName;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "departure")
    @JsonManagedReference
    private List<DeparturePost> departurePosts = new ArrayList<>();
}
