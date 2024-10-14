package com.e_mail.item_post.entity;

import com.e_mail.item_post.common.Status;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "departures-posts")
@Getter
@Setter
public class DeparturePost {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "departure_id")
    private Departure departure;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @Column(name = "when_arrived")
    private Date whenArrived;

    @Enumerated(EnumType.STRING)
    @Column(name = "new_status")
    private Status status;
}
