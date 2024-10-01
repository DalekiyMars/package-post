package com.e_mail.item_post.db.service;

import com.e_mail.item_post.constants.Constants;
import com.e_mail.item_post.db.repository.MailPostRepository;
import com.e_mail.item_post.entity.Post;
import com.e_mail.item_post.util.DtoBadRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

    public Post getPostById(int id){
        return mailPostRepository.findById(id).orElseThrow(() -> new DtoBadRequestException(Constants.ExceptionMessages.INCORRECT_POST));
    }

    public Post searchPost(String oldPostName){
        return mailPostRepository.findByName(oldPostName).orElseThrow(() -> new DtoBadRequestException(Constants.ExceptionMessages.INCORRECT_POST));
    }

}
