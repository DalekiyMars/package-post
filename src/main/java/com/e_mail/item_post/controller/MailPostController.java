package com.e_mail.item_post.controller;

import com.e_mail.item_post.db.service.PostService;
import com.e_mail.item_post.dto.PostDto;
import com.e_mail.item_post.entity.Post;
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

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
@Slf4j
public class MailPostController {

    private final ModelMapper modelMapper;
    private final PostRequestExceptionHandler exceptionHandler;
    private final PostService postService;

    @PostMapping("/new")
    public ResponseEntity<HttpStatus> addNewPost(@RequestBody @Valid PostDto postDto,
                                                 BindingResult result){
        if (result.hasErrors()){
            throw new DtoBadRequestException(exceptionHandler.generateMessageAboutErrors(result));
        }
        var temp =  postService.save(modelMapper.map(postDto, Post.class));
        log.info("Post с id " + temp.getIndex() + " сохранен");
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public PostDto getPostById(@PathVariable("id") int id){
        return modelMapper.map(postService.getPostById(id), PostDto.class);
    }

    @PostMapping("/update/{name}")
    public ResponseEntity<HttpStatus> updatePostInfo(@PathVariable("name") String name, @RequestBody PostDto postDto){
        var temp = postService.searchPost(name);
        updateDataAboutCurrentPost(temp.getId(), postDto);
        return ResponseEntity.ok(HttpStatus.ACCEPTED);
    }

    @GetMapping
    public List<PostDto> getMailPosts(){
        return postService.getAllPosts().stream()
                .map(post -> modelMapper.map(post, PostDto.class))
                .collect(Collectors.toList());
    }

    @DeleteMapping("/delete/{name}")
    public ResponseEntity<HttpStatus> deletePost(@PathVariable("name") String name){
        postService.delete(name);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    public void updateDataAboutCurrentPost(int postId, PostDto postDto){
        var updatedPost = modelMapper.map(postDto, Post.class);
        updatedPost.setId(postId);
        postService.save(updatedPost);
        log.info("Пост "+ updatedPost.getName() + " был обновлен");
    }
}
