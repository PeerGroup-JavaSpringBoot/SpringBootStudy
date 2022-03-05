package dev.yoon.basic_board.repository;



import dev.yoon.basic_board.domain.Post;

import java.util.List;

public interface PostRepository {

    void save(Post post);

    List<Post> findAll();

    Post findById(Long id);

    void delete(Post post);


}
