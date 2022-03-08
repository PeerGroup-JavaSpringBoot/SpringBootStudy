package jsbdy.mission3.dao;

import jsbdy.mission3.entity.BoardEntity;
import jsbdy.mission3.entity.PostEntity;
import jsbdy.mission3.entity.UserEntity;
import jsbdy.mission3.model.PostDto;
import jsbdy.mission3.repository.BoardRepository;
import jsbdy.mission3.repository.PostRepository;
import jsbdy.mission3.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;

import java.util.Iterator;
import java.util.Optional;

@Repository
public class PostDao {
    private static final Logger logger = LoggerFactory.getLogger(PostDao.class);
    private final PostRepository postrepository;
    private final UserRepository userrepository;
    private final BoardRepository boardrepository;

    //postrepository 지정해주기
    public PostDao(
            @Autowired PostRepository postrepository,
            @Autowired UserRepository userrepository,
            @Autowired BoardRepository boardrepository
    ){
        this.postrepository=postrepository;
        this.userrepository=userrepository;
        this.boardrepository=boardrepository;
    }
    public void createPost(PostDto postdto, Long userid, Long boardid){

        Optional<UserEntity> optionalEntity = userrepository.findById(userid);
        UserEntity userEntity =optionalEntity.get();
        Optional<BoardEntity> boptionalEntity = boardrepository.findById(boardid);
        BoardEntity boardEntity = boptionalEntity.get();
        PostEntity postentity = new PostEntity();
        postentity.setTitle(postdto.getTitle());
        postentity.setContent(postdto.getContent());
        postentity.setWriter(userEntity.getName());
        postentity.setBoardentity(boardEntity);
        postentity.setUserentity(userEntity);
        //header에서 받은 userid를 userrepository에서 그 아이디로 객체 찾아서 entity가 그 객체로 user set하게 해줌
        this.postrepository.save(postentity);
    }

    public PostEntity readPost(int id){
        Optional<PostEntity> postentity = this.postrepository.findById((long) id);
        if(postentity.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return postentity.get();

    }

    public Iterator<PostEntity> readPostAll(){
        return this.postrepository.findAll().iterator();
    }


    public void updatePost(int id, PostDto postdto){
        Optional<PostEntity> targetentity = this.postrepository.findById(Long.valueOf(id));
        if (targetentity.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        PostEntity postentity=targetentity.get();

        postentity.setTitle(postdto.getTitle()==null?postentity.getTitle() : postdto.getTitle());
        postentity.setContent(postdto.getContent()==null?postentity.getContent() : postdto.getContent());
        postentity.setWriter(postdto.getWriter()==null?postentity.getWriter() : postdto.getWriter());
        postentity.setBoardentity(null);
        postentity.setUserentity(null);

        this.postrepository.save(postentity);
    }
    public void deletePost(int id){
        Optional<PostEntity> targetentity = this.postrepository.findById((long) id);
        if(targetentity.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        this.postrepository.delete(targetentity.get());
    }

}
