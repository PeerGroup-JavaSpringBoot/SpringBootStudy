package jsbdy.mission3.dao;

import jsbdy.mission3.entity.BoardEntity;
import jsbdy.mission3.entity.PostEntity;
import jsbdy.mission3.entity.UserEntity;
import jsbdy.mission3.model.BoardDto;
import jsbdy.mission3.model.UserDto;
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
public class UserDao {
    private static final Logger logger = LoggerFactory.getLogger(PostDao.class);
    private final PostRepository postrepository;
    private final UserRepository userrepository;

    public UserDao(
            @Autowired PostRepository postrepository,
            @Autowired UserRepository userrepository
            ) {
        this.postrepository = postrepository;
        this.userrepository = userrepository;
    }

    public void createUser(UserDto userdto) {
//        private Long id;
//        private String name;
//        private List<PostEntity> postentitylist = new ArrayList<>();
        UserEntity userEntity = new UserEntity();
        userEntity.setName(userdto.getName());
//        userEntity.setPostentitylist(postrepository.findAllByUserentity(userEntity));
        this.userrepository.save(userEntity);
    }

    public UserEntity readUser(int id) {
        Optional<UserEntity> userEntity = this.userrepository.findById((long) (id));
        if (userEntity.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return userEntity.get();
    }

    public Iterator<UserEntity> readUserAll() {
        return this.userrepository.findAll().iterator();
    }


    public void updateUser(int id, UserDto userdto) {
        Optional<UserEntity> targetentity = this.userrepository.findById(Long.valueOf(id));
        if (targetentity.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        UserEntity userEntity = targetentity.get();
        userEntity.setName(userdto.getName() == null ? userEntity.getName() : userdto.getName());
        //업데이트로 넘어온 항목이 없으면 본래 엔티티에 있는 이름 데려오고
        //있으면 dto로 새로 넘어온 애에서 이름 데려오면 된다.

        this.userrepository.save(userEntity);
    }

    public void deleteUser(int id) {
        Optional<UserEntity> targetentity = this.userrepository.findById((long) id);
        if (targetentity.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        this.userrepository.delete(targetentity.get());
    }
}