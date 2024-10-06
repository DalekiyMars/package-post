package com.e_mail.item_post.db.service;

import com.e_mail.item_post.db.repository.DeparturePostRepository;
import com.e_mail.item_post.entity.DeparturePost;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DeparturePostService {
    private final DeparturePostRepository departurePostRepository;

    @Transactional
    public void saveDepartureAndUpdatedPost(DeparturePost departurePost){
        departurePostRepository.save(departurePost);
    }
}
