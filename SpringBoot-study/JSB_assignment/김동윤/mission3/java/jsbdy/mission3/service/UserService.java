package jsbdy.mission3.service;


import jsbdy.mission3.dao.BoardDao;
import jsbdy.mission3.dao.UserDao;
import jsbdy.mission3.entity.BoardEntity;
import jsbdy.mission3.entity.PostEntity;
import jsbdy.mission3.entity.UserEntity;
import jsbdy.mission3.model.BoardDto;
import jsbdy.mission3.model.UserDto;
import jsbdy.mission3.repository.PostRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class UserService{
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);
    private final UserDao userdao; //postdao 추가 선언 (활용 위해)

    public UserService(@Autowired UserDao userdao){
        this.userdao = userdao; //postdao 초기화
    }

    public void createUser(UserDto userdto){
        this.userdao.createUser(userdto);
    }

    public UserDto readBoard(int id){
        UserEntity userEntity = this.userdao.readUser(id);
//        List<PostEntity> postentitylist = new ArrayList<>();
        return new UserDto(
                userEntity.getId(),
                userEntity.getName()==null?null:(userEntity.getName()),
                userEntity.getPostentitylist()==null?null:(userEntity.getPostentitylist())
        );
    }

    public List<UserDto> readUserAll(){
        Iterator<UserEntity> iterator=this.userdao.readUserAll();
        List<UserDto> userDtoList = new ArrayList<>();

        while(iterator.hasNext()){
            UserEntity userEntity = iterator.next();
            userDtoList.add(new UserDto(
                    userEntity.getId(),
                    userEntity.getName(),
                    userEntity.getPostentitylist()
            ));
        }
        return userDtoList;
    }

    public void updateUser(int id, UserDto userDto){
        this.userdao.updateUser(id,userDto);
    }

    public void deleteUser(int id){
        this.userdao.deleteUser(id);
    }
}