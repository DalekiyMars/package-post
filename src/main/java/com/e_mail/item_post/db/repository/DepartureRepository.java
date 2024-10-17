package com.e_mail.item_post.db.repository;

import com.e_mail.item_post.entity.Departure;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface DepartureRepository extends JpaRepository<Departure, Integer> {
    Optional<Departure> findById(UUID uuid);
}
