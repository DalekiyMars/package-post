package com.e_mail.item_post.controller;

import com.e_mail.item_post.db.service.DeparturePostService;
import com.e_mail.item_post.db.service.DepartureService;
import com.e_mail.item_post.db.service.PostService;
import com.e_mail.item_post.dto.DeparturePostDto;
import com.e_mail.item_post.entity.DeparturePost;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/update-status")
@Slf4j
@RequiredArgsConstructor
public class DeparturePostController {
    private final DeparturePostService departurePostService;
    private final DepartureService departureService;
    private final PostService postService;

    @PostMapping("/new")
    public ResponseEntity<HttpStatus> saveDeparturePost(@RequestBody
                                                        @Validated DeparturePostDto departurePostDto){
        DeparturePost departurePost = new DeparturePost();
        departurePost.setDeparture(departureService.findOne(departurePostDto.getDepartureId()));
        departurePost.setPost(postService.searchPost(departurePostDto.getPostId()));
        departurePost.setStatus(departurePostDto.getUpdatedStatus());
        departurePostService.saveDepartureAndUpdatedPost(departurePost);

        return ResponseEntity.ok(HttpStatus.OK);
    }

//    @PostMapping("/{departure_id}")
//    public DeparturePostDto searchDeparturePost(@RequestBody
//                                                        @Validated UUID departure_id){
//        //TODO вывод экземпляра через id у departure
//
//        //return ResponseEntity.ok(HttpStatus.OK);
//    }


}
