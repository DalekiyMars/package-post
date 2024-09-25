package com.e_mail.item_post.db.repository;

import com.e_mail.item_post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MailPostRepository extends JpaRepository<Post, Long> {
}
