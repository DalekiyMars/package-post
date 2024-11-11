package com.e_mail.item_post.db.service;

import com.e_mail.item_post.constants.Constants;
import com.e_mail.item_post.db.repository.MailPostRepository;
import com.e_mail.item_post.dto.PostDto;
import com.e_mail.item_post.entity.Post;
import com.e_mail.item_post.util.DtoBadRequestException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class PostService {
    private final MailPostRepository mailPostRepository;

    @Autowired
    ModelMapper mapper;

    @Transactional
    public Post save(PostDto postDto){
        return mailPostRepository.save(mapper.map(postDto, Post.class));
    }

    @Transactional
    public void updateDataAboutCurrentPost(long postId, Post updatedPost) {
        updatedPost.setId(postId);
        mailPostRepository.save(updatedPost);
        log.info("Пост " + updatedPost.getName() + " был обновлен");
    }

    public List<Post> getAllPosts(){
        return mailPostRepository.findAll();
    }

    public Post getPostById(long id){
        return mailPostRepository.findById(id)
                .orElseThrow(() -> new DtoBadRequestException(Constants.ExceptionMessages.INCORRECT_POST));
    }

    @Transactional
    public void delete(long id){
        var foundPost = getPostById(id);
        log.info(foundPost.toString() + " будет удален");
        mailPostRepository.delete(foundPost);

    }
}
