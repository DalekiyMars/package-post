package com.e_mail.item_post.db.repository;

import com.e_mail.item_post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface MailPostRepository extends JpaRepository<Post, Integer> {
    Optional<Post> findById(UUID uuid);

}
