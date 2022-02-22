package dev.yoon.basic_board.service;

import dev.yoon.basic_board.dto.PostDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface PostService {

    void createPost(PostDto postDto) throws Exception;

    List<PostDto> readPostAll();

    PostDto readPostOne(Long id);

    void updatePost(Long id, PostDto postDto);

    void deletePost(Long id,String pw);
}
