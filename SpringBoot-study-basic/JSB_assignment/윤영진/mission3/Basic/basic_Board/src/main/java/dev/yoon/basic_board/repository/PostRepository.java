package dev.yoon.basic_board.repository;



import dev.yoon.basic_board.domain.Post;
import dev.yoon.basic_board.dto.PostDto;

import java.util.List;

public interface PostRepository {

    void save(Post post);

    List<Post> findPostAllbyBoardId(Long boardId);

    Post findPostOnebyBoardId(Long boardId, Long postId);

    boolean updatePost(Long boardId, Long postId, PostDto postDto);

    boolean deletePost(Long boardId, Long postId,String pw);


}
