package com.e_mail.item_post.db.service;

import com.e_mail.item_post.db.repository.MailPostRepository;
import com.e_mail.item_post.entity.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MailPostService{
    private final MailPostRepository mailPostRepository;

    @Transactional
    public Post save(Post post){
        return mailPostRepository.save(post);
    }
}
