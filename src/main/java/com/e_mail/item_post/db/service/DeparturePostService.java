package com.e_mail.item_post.db.service;

import com.e_mail.item_post.db.repository.DeparturePostRepository;
import com.e_mail.item_post.dto.DeparturePostDto;
import com.e_mail.item_post.entity.DeparturePost;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DeparturePostService {
    private final DeparturePostRepository departurePostRepository;
    private final DepartureService departureService;
    private final PostService postService;

    public DeparturePost searchDepartureAndPost(DeparturePostDto departurePostDto){
        DeparturePost departurePost = new DeparturePost();
        departurePost.setDeparture(departureService.findOne(departurePostDto.getDepartureId()));
        departurePost.setPost(postService.searchPost(departurePostDto.getPostAddress()));
        departurePost.setStatus(departurePostDto.getUpdatedStatus());
        return departurePost;
    }

    @Transactional
    public void saveDepartureAndUpdatedPost(DeparturePostDto departurePostDto){
        departurePostRepository.save(searchDepartureAndPost(departurePostDto));
    }
}
