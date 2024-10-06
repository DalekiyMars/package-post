package com.e_mail.item_post.controller;

import com.e_mail.item_post.db.service.PostService;
import com.e_mail.item_post.dto.PostDto;
import com.e_mail.item_post.entity.Post;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
@Slf4j
public class MailPostController {

    private final ModelMapper modelMapper;
    private final PostService postService;

    @PostMapping("/new")
    public ResponseEntity<HttpStatus> addNewPost(@RequestBody @Validated PostDto postDto){
        var temp =  postService.save(modelMapper.map(postDto, Post.class));
        log.info("Post с id " + temp.getIndex() + " сохранен");
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public PostDto getPostById(@PathVariable("id") int id){
        return modelMapper.map(postService.getPostById(id), PostDto.class);
    }

    @PostMapping("/update/{id}")
    public ResponseEntity<HttpStatus> updatePostInfo(@PathVariable("id") int id, @RequestBody @Validated PostDto postDto){
        var temp = postService.searchPost(id);
        updateDataAboutCurrentPost(temp.getId(), postDto);
        return ResponseEntity.ok(HttpStatus.ACCEPTED);
    }

    @GetMapping
    public List<PostDto> getMailPosts(){
        return postService.getAllPosts().stream()
                .map(post -> modelMapper.map(post, PostDto.class))
                .collect(Collectors.toList());
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<HttpStatus> deletePost(@PathVariable("id") int id){
        postService.delete(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    public void updateDataAboutCurrentPost(int postId, PostDto postDto){
        var updatedPost = modelMapper.map(postDto, Post.class);
        updatedPost.setId(postId);
        postService.save(updatedPost);
        log.info("Пост "+ updatedPost.getName() + " был обновлен");
    }
}
