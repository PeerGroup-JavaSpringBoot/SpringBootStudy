package dev.yoon.basic_board.service;

import dev.yoon.basic_board.dto.PostDto;

import java.util.List;

public interface PostService {

    PostDto createPost(Long boardId, PostDto postDto);

    List<PostDto> readPostAllbyBoardId(Long id);

    PostDto readPostOneByBoardId(Long boardId, Long postId);

    boolean updatePost(Long boardId, Long postId, PostDto postDto);

    boolean deletePost(Long boardId, Long postId, PostDto postDto);

}
