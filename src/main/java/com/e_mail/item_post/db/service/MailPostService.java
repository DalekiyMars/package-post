package com.e_mail.item_post.db.service;

import com.e_mail.item_post.db.repository.MailPostRepository;
import com.e_mail.item_post.entity.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MailPostService{
    private final MailPostRepository mailPostRepository;

    @Transactional
    public Post save(Post post){
        return mailPostRepository.save(post);
    }

    public List<Post> getAllPosts(){
        return mailPostRepository.findAll();
    }

    public Optional<Post> searchPost(String oldPostName){
        return mailPostRepository.findByName(oldPostName);
    }

    @Transactional
    public Optional<Post> updatePost(Post post){
        return mailPostRepository.updatePostById(post.getId());
    }
}
