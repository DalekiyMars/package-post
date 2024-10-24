package com.e_mail.item_post.controller;

import com.e_mail.item_post.db.service.PostService;
import com.e_mail.item_post.dto.PostDto;
import com.e_mail.item_post.entity.Post;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Posts", description = "Posts API to manipulate it")
public class MailPostController {

    private final ModelMapper modelMapper;
    private final PostService postService;

    @Operation(
            summary = "Create new post",
            description = "Enters new post to database",
            parameters = {
                    @Parameter(name = "id", description = "Post ID")
            },
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(
                            schema = @Schema(implementation = PostDto.class)
                    )
            ),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Post added",
                            content = @Content
                    ),
                    @ApiResponse(responseCode = "400", description = "Incorrect data",
                            content = @Content
                    )
            }
    )
    @PostMapping("/new")
    public ResponseEntity<HttpStatus> addNewPost(@RequestBody @Validated PostDto postDto) {
        var temp = postService.save(modelMapper.map(postDto, Post.class));
        log.info("Post с id " + temp.getId() + " сохранен");
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @Operation(
            summary = "Returns post by id",
            description = "Search post by id in database and return it if exists",
            parameters = {
                    @Parameter(name = "id", description = "Post ID")
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Post exists",
                            content = @Content(
                                    schema = @Schema(implementation = PostDto.class),
                                    mediaType = "JSON")
                    ),
                    @ApiResponse(responseCode = "400", description = "Incorrect id",
                            content = @Content
                    )
            }
    )
    @GetMapping("/{id}")
    public PostDto getPostById(@PathVariable("id") long id) {
        return modelMapper.map(postService.getPostById(id), PostDto.class);
    }

    @Operation(
            summary = "Update post info",
            description = "Search post by id and update its information if exists (also validate new data)",
            parameters = {
                    @Parameter(name = "id", description = "Post ID")
            },
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(
                            schema = @Schema(implementation = PostDto.class)
                    )
            ),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Post updated",
                            content = @Content
                    ),
                    @ApiResponse(responseCode = "400", description = "Incorrect id or data",
                            content = @Content
                    )
            }
    )
    @PostMapping("/update/{id}")
    public ResponseEntity<HttpStatus> updatePostInfo(@PathVariable("id") long id, @RequestBody @Validated PostDto postDto) {
        var temp = postService.searchPost(id);
        postService.updateDataAboutCurrentPost(temp.getId(), modelMapper.map(postDto, Post.class));
        return ResponseEntity.ok(HttpStatus.ACCEPTED);
    }

    @Operation(
            summary = "Return posts",
            description = "Search all posts and return it",
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK",
                            content = @Content(
                                    schema = @Schema(implementation = PostDto.class),
                                    mediaType = "JSON"
                            )
                    ),

            }
    )
    @GetMapping
    public List<PostDto> getMailPosts() {
        return postService.getAllPosts().stream()
                .map(post -> modelMapper.map(post, PostDto.class))
                .collect(Collectors.toList());
    }

    @Operation(
            summary = "Deletes post",
            description = "Search post in database and deletes it",
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK",
                            content = @Content
                    ),
                    @ApiResponse(responseCode = "400", description = "Incorrect id",
                            content = @Content
                    )
            }
    )
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<HttpStatus> deletePost(@PathVariable("id") long id) {
        postService.delete(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }
}
