package com.e_mail.item_post.controller;

import com.e_mail.item_post.db.service.MailPostService;
import com.e_mail.item_post.dto.PostDto;
import com.e_mail.item_post.entity.Post;
import com.e_mail.item_post.util.RequestErrorResponse;
import com.e_mail.item_post.util.DtoBadRequestException;
import com.e_mail.item_post.util.PostRequestExceptionHandler;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
@Slf4j
public class MailPostController {

    private final ModelMapper modelMapper;
    private final PostRequestExceptionHandler exceptionHandler;
    private final MailPostService postService;

    @PostMapping("/new")
    public ResponseEntity<HttpStatus> addNewPost(@RequestBody @Valid PostDto postDto,
                                                 BindingResult result){
        if (result.hasErrors()){
            throw new DtoBadRequestException(exceptionHandler.generateMessageAboutErrors(result));
        }
        var temp =  postService.save(convertToPost(postDto));
        log.info("Post с id " + temp.getIndex() + " сохранен");
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @ExceptionHandler
    private ResponseEntity<RequestErrorResponse> departureError(DtoBadRequestException e) {
        RequestErrorResponse response = new RequestErrorResponse(
                e.getMessage(),
                System.currentTimeMillis()
        );
        log.error("Пользователь не cоздан в  " + response.getDateTime() + " - недопустимые данные");
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    public Post convertToPost(PostDto dto) {
        return modelMapper.map(dto, Post.class);
    }

    public PostDto convertToDTO(Post post) {
        return modelMapper.map(post, PostDto.class);
    }
}
