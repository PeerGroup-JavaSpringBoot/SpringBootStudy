package jsbdy.mission2.post;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class PostService implements PostServicei {
    private static final Logger logger = LoggerFactory.getLogger(PostService.class);

    private final PostRepositoryi postRepository;

    public PostService(
            @Autowired PostRepositoryi postRepository
    ) {
        this.postRepository = postRepository;
    }


    @Override
    public List<PostDto> readPostAll() {
        return this.postRepository.findAll();
    }

    @Override
    public void createPost(PostDto dto) {
        if(!this.postRepository.save(dto)){
            throw new RuntimeException(("save failed"));
        }
//        this.postRepository.save(dto);
    }
    @Override
    public PostDto readPost(int id, int board) {
        if(this.postRepository.findById(id).getBoard_id()!=board) {
            throw new RuntimeException(("read failed"));
        }
        return this.postRepository.findById(id);
    }

    @Override
    public void updatePost(int id, PostDto dto) {
        this.postRepository.update(id,dto);

    }

    @Override
    public void deletePost(int id,String pw) {
        logger.info(pw);//"URL 타고 넘어온 유저가 작성한 비번 : " ,
        logger.info(postRepository.findById(id).getPassword());//"ID에 해당하는 POST의 비번 : " ,
        if(!Objects.equals(pw,(this.postRepository.findById(id).getPassword()))){
            logger.info("delete failed - PASSWORD ERROR");
            throw new RuntimeException();
        }
        this.postRepository.delete(id,pw);
        logger.info("delete successed - PASSWORD EQUALS");
    }
}
