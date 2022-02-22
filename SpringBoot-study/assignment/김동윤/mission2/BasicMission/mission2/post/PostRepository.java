package jsbdy.mission2.post;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
@Repository
//포스트 메모리지만 메모리 뒤에 구현된다
public class PostRepository implements PostRepositoryi{

    private static final Logger logger = LoggerFactory.getLogger(PostRepository.class);
    private final List<PostDto>postList;

    public PostRepository(){
        this.postList = new ArrayList<>();
    }


    @Override
    public boolean save(PostDto dto) {
        return this.postList.add(dto);
        //-> add가 애초에 boolean값을 돌려준
        //return true;
    }

    @Override
    public PostDto findById(int id) {
        return this.postList.get(id);
    }

    @Override
    public List<PostDto> findAll() {
        return this.postList;
    }

    @Override
    public boolean update(int id, PostDto dto) {
        PostDto targetPost = this.postList.get(id); //업데이트를 위한 목적 타겟
        if (dto.getTitle()!=null){
            targetPost.setTitle(dto.getTitle());
        }
        if (dto.getContent()!=null){
            targetPost.setContent(dto.getContent());
        }
        this.postList.set(id, targetPost);

        return true;
    }
    //return 값은 성공 여부 알려주는 것
    @Override
    public boolean delete(int id, String pw) {
        this.postList.remove(id);
        return true;
    }
}