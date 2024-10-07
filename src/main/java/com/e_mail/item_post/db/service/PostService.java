package com.e_mail.item_post.db.service;

import com.e_mail.item_post.constants.Constants;
import com.e_mail.item_post.db.repository.MailPostRepository;
import com.e_mail.item_post.entity.Post;
import com.e_mail.item_post.util.DtoBadRequestException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class PostService {
    private final MailPostRepository mailPostRepository;

    @Transactional
    public Post save(Post post){
        return mailPostRepository.save(post);
    }

    public List<Post> getAllPosts(){
        return mailPostRepository.findAll();
    }

    public Post getPostById(UUID id){
        return mailPostRepository.findById(id).orElseThrow(() -> new DtoBadRequestException(Constants.ExceptionMessages.INCORRECT_POST));
    }

    public Post searchPost(UUID id){
        return mailPostRepository.findById(id).orElseThrow(() -> new DtoBadRequestException(Constants.ExceptionMessages.INCORRECT_POST));
    }

    @Transactional
    public void delete(UUID id){
        var foundPost = searchPost(id);
        log.info(foundPost.toString() + " будет удален");
        mailPostRepository.delete(foundPost);

    }
}
