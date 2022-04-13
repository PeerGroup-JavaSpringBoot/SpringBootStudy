package jsbdy.mission3.repository;

import jsbdy.mission3.entity.PostEntity;
import jsbdy.mission3.entity.UserEntity;
import jsbdy.mission3.model.PostDto;
import org.springframework.data.repository.CrudRepository;

import java.util.Collection;
import java.util.List;

public interface PostRepository extends CrudRepository<PostEntity, Long>{
//    List<PostEntity> findAllByWriter(String writer);
    List<PostEntity> findAllByUserentity(UserEntity entity);
//    PostDto create(Long boardId, PostDto dto);
//    PostDto read(Long boardId, Long postId);
//    Collection<PostDto> readAll (Long boardId);
//    boolean update(Long boardId, Long postId, PostDto dto);
//    boolean delete(Long boardId, Long postId, String password);
}
