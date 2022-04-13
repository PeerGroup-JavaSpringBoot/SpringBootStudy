package dev.yoon.board.service;

import dev.yoon.board.dto.PostDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface PostService {

    void createPost(PostDto postDto, List<MultipartFile> files) throws Exception;

    List<PostDto> readPostAll();

    PostDto readPostOne(Long id);

    void updatePost(Long id, PostDto postDto, List<MultipartFile> addFileList);

    void deletePost(Long id,String pw);
}
