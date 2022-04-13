package jsbdy.mission2.post;

import java.util.List;

public interface PostRepositoryi {
    boolean save(PostDto dto);
    PostDto findById(int id); //id를 주게 되면 PostDto가 돌아가게 된다
    List<PostDto> findAll();
    boolean update(int id, PostDto dto);
    boolean delete(int id, String pw);
}