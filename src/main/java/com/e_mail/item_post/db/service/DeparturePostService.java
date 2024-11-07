package com.e_mail.item_post.db.service;

import com.e_mail.item_post.constants.Constants;
import com.e_mail.item_post.db.repository.DeparturePostRepository;
import com.e_mail.item_post.dto.DeparturePostDto;
import com.e_mail.item_post.dto.DeparturePostEnterprise;
import com.e_mail.item_post.entity.DeparturePost;
import com.e_mail.item_post.util.DtoBadRequestException;
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
    public DeparturePost saveDepartureAndUpdatedPost(DeparturePostDto departurePostDto){
        log.info(departurePostDto.toString() + " saved");
        return departurePostRepository.save(searchDepartureAndPost(departurePostDto));
    }

    public List<DeparturePostEnterprise> getHistoryAboutDeparture(UUID departureId){
        var takenList = departurePostRepository.getDeparturePostByDeparture_Id(departureId);
        log.info("Departure with UUID: " + departureId + " got " + takenList.size() + " records");

        if (takenList.isEmpty()) throw new DtoBadRequestException(Constants.ExceptionMessages.ENTITY_NOT_EXISTS);
        return takenList.stream().map(entityConverter::convertToEnterprise)
                .collect(Collectors.toList());
    }

    @Transactional
    public void deleteDepartureAndPostHistory(UUID departureId){
        var entityExists = departurePostRepository.existsDeparturePostByDeparture_Id(departureId);
        if (!entityExists) throw new DtoBadRequestException(Constants.ExceptionMessages.ENTITY_NOT_EXISTS);
        log.info("Records with departures UUID: " + departureId + " deleted");
        departurePostRepository.deleteAllByDeparture_Id(departureId);
    }

}
