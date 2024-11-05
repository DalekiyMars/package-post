package com.e_mail.item_post.db.repository;

import com.e_mail.item_post.entity.DeparturePost;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface DeparturePostRepository extends JpaRepository<DeparturePost, Integer> {
     List<DeparturePost> getDeparturePostByDeparture_Id(UUID departureId);
     void deleteAllByDeparture_Id(UUID departureId);
     boolean existsDeparturePostByDeparture_Id(UUID departureId);
}
