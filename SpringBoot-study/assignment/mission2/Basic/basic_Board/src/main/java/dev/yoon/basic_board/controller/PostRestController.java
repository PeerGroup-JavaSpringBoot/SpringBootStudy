package dev.yoon.basic_board.controller;


import dev.yoon.basic_board.dto.PostDto;
import dev.yoon.basic_board.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("post")
@RequiredArgsConstructor
public class PostRestController {

    private final PostService postService;

    @PostMapping(value = "{boardId}")
    @ResponseStatus(HttpStatus.CREATED)
    public void createPost(
            @PathVariable("boardId") Long id,
            @RequestBody PostDto postDto,
            HttpServletRequest request) throws Exception {

        postDto.setBoardId(id);
//        log.info(request.getHeader("Content-Type"));
        this.postService.createPost(postDto);
    }

    @GetMapping()
    public List<PostDto> readPostAll() {
        log.info("in read post all");
        List<PostDto> postDtos = this.postService.readPostAll();
        return postDtos;
    }

    @GetMapping("{id}")
    public PostDto readPostOne(@PathVariable("id") Long id) {
        log.info("in read post one");
        return this.postService.readPostOne(id);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("{id}")
    public void updatePost(
            @PathVariable("id") Long id,
            @RequestBody PostDto postDto) {
        log.info("target id: " + id);
        log.info("update content: " + postDto);

        this.postService.updatePost(id, postDto);


    }

    @ResponseStatus(HttpStatus.ACCEPTED)
    @DeleteMapping("{id}")
    public void deletePost(@PathVariable("id") Long id, @RequestBody PostDto postDto) {

        this.postService.deletePost(id, postDto.getPw());
    }

}
