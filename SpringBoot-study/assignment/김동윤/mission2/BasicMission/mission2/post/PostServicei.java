package jsbdy.mission2.post;

import java.util.List;

public interface PostServicei {
    void createPost(PostDto dto);
    List<PostDto> readPostAll();
    PostDto readPost(int id, int board);
    void updatePost(int id, PostDto dto);
    void deletePost(int id, String pw);
}