package com.e_mail.item_post.db.service;

import com.e_mail.item_post.db.repository.DeparturePostRepository;
import com.e_mail.item_post.dto.DeparturePostDto;
import com.e_mail.item_post.dto.DeparturePostEnterprise;
import com.e_mail.item_post.entity.DeparturePost;
import com.e_mail.item_post.util.EntityConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DeparturePostService {
    private final DeparturePostRepository departurePostRepository;
    private final DepartureService departureService;
    private final PostService postService;
    private final EntityConverter entityConverter;
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

    public List<DeparturePostEnterprise> getHistoryAboutDeparture(UUID departureId){
        var temp = departurePostRepository.getDeparturePostByDeparture_Id(departureId);
        return temp.stream().map(entityConverter::convertToEnterprise).collect(Collectors.toList());
    }
}
