package dev.yoon.board.repository;

import dev.yoon.board.domain.File;
import dev.yoon.board.domain.Post;
import java.util.List;

public interface PostRepository {

    void save(Post post);

    List<Post> findAll();

    Post findById(Long id);

    void delete(Post post);

    List<File> findAllByPost(Long id);

}
