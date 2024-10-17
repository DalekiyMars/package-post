package com.e_mail.item_post.entity;

import com.e_mail.item_post.common.PackageType;
import com.e_mail.item_post.common.Status;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.UuidGenerator;
import org.hibernate.proxy.HibernateProxy;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "departures")
@Accessors(chain = true)
@Getter
@Setter
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
    private List<DeparturePost> departurePosts = new ArrayList<>();

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        Departure departure = (Departure) o;
        return getId() != null && Objects.equals(getId(), departure.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}
