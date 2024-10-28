package com.e_mail.item_post.db.service;

import com.e_mail.item_post.db.repository.DeparturePostRepository;
import com.e_mail.item_post.dto.DeparturePostDto;
import com.e_mail.item_post.dto.DeparturePostEnterprise;
import com.e_mail.item_post.entity.DeparturePost;
import com.e_mail.item_post.util.EntityConverter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class DeparturePostService {
    private final DeparturePostRepository departurePostRepository;
    private final DepartureService departureService;
    private final PostService postService;
    private final EntityConverter entityConverter;
    public DeparturePost searchDepartureAndPost(DeparturePostDto departurePostDto){
        DeparturePost departurePost = new DeparturePost();
        departurePost.setDeparture(departureService.findOne(departurePostDto.getDepartureId()));
        departurePost.setPost(postService.getPostById(departurePostDto.getPostAddress()));
        departurePost.setStatus(departurePostDto.getUpdatedStatus());
        departurePost.setWhenArrived(new Date());
        log.info(departurePost + " found in DB");
        return departurePost;
    }

    @Transactional
    public void saveDepartureAndUpdatedPost(DeparturePostDto departurePostDto){
        log.info(departurePostDto.toString() + " saved");
        departurePostRepository.save(searchDepartureAndPost(departurePostDto));
    }

    public List<DeparturePostEnterprise> getHistoryAboutDeparture(UUID departureId){
        var temp = departurePostRepository.getDeparturePostByDeparture_Id(departureId);
        log.info("Departure with UUID: " + departureId + " got " + temp.size() + " records");
        return temp.stream().map(entityConverter::convertToEnterprise).collect(Collectors.toList());
    }

    @Transactional
    public void deleteDepartureAndPostHistory(UUID departureId){
        log.info("Records with departures UUID: " + departureId + " deleted");
        departurePostRepository.deleteAllByDeparture_Id(departureId);
    }

}
