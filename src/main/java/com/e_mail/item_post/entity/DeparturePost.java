package com.e_mail.item_post.entity;

import com.e_mail.item_post.common.Status;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "departures-posts")
@Data
public class DeparturePost {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "departure_id")
    private Departure departure;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @Column(name = "when_arrived")
    private LocalDateTime whenArrived = LocalDateTime.now();

    @Enumerated(EnumType.STRING)
    @Column(name = "new_status")
    private Status status;
}
