package jsbdy.mission3.service;


import jsbdy.mission3.dao.UserDao;
import jsbdy.mission3.entity.PostEntity;
import jsbdy.mission3.entity.UserEntity;
import jsbdy.mission3.model.PostDto;
import jsbdy.mission3.dao.PostDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class PostService{
    private static final Logger logger = LoggerFactory.getLogger(PostService.class);
    private final PostDao postdao; //postdao 추가 선언 (활용 위해)
    private final UserDao userdao;

    public PostService(@Autowired PostDao postdao,
                       @Autowired UserDao userdao
    ){
        this.postdao = postdao; //postdao 초기화
        this.userdao = userdao;
    }

    public void createPost(PostDto postdto, Long userid, Long boardid){
        this.postdao.createPost(postdto, userid, boardid);
    }

    public PostDto readPost(int id){
        PostEntity postentity = this.postdao.readPost(id);
        UserEntity userentity = this.userdao.readUser(id);
        return new PostDto(
                postentity.getId(),
                postentity.getTitle(),
                postentity.getContent(),
                postentity.getWriter(),
                postentity.getPassword(),
                postentity.getBoardentity()==null?null:(postentity.getBoardentity().getId()),
                postentity.getUserentity()==null?null:(userentity.getId())
        );
    }

    public List<PostDto> readPostAll(){
        Iterator<PostEntity> iterator=this.postdao.readPostAll();
        List<PostDto> postDtoList = new ArrayList<>();

        while(iterator.hasNext()){
            PostEntity postentity = iterator.next();
            //userentity는 postentity에 등록된 user엔티티를 데려오기
            UserEntity userentity = postentity.getUserentity();
            postDtoList.add(new PostDto(
                    postentity.getId(),
                    postentity.getTitle(),
                    postentity.getContent(),
                    postentity.getWriter(),
                    postentity.getPassword(),
                    postentity.getBoardentity()==null?null:(postentity.getBoardentity().getId()),
                    postentity.getUserentity()==null?null:(userentity.getId())

            ));
        }
        return postDtoList;
    }

    public void updatePost(int id, PostDto postdto){
        this.postdao.updatePost(id,postdto);
    }

    public void deletePost(int id){
        this.postdao.deletePost(id);
    }
}