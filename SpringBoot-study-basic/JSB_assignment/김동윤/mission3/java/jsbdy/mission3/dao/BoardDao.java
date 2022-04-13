package jsbdy.mission3.dao;


import jsbdy.mission3.entity.BoardEntity;
import jsbdy.mission3.entity.PostEntity;
import jsbdy.mission3.entity.UserEntity;
import jsbdy.mission3.model.BoardDto;
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

import javax.persistence.Column;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Repository
public class BoardDao {
    private static final Logger logger = LoggerFactory.getLogger(PostDao.class);
    private final PostRepository postrepository;
    private final UserRepository userrepository;
    private final BoardRepository boardrepository;

    //postrepository 지정해주기
    public BoardDao(
            @Autowired PostRepository postrepository,
            @Autowired UserRepository userrepository,
            @Autowired BoardRepository boardrepository
    ){
        this.postrepository=postrepository;
        this.userrepository=userrepository;
        this.boardrepository=boardrepository;
    }
    public void createBoard(BoardDto boarddto){
//        private Long id;
//        private String name;
//        private List<PostEntity> postentitylist = new ArrayList<>();
        BoardEntity boardentity = new BoardEntity();
        boardentity.setName(boarddto.getName());
        this.boardrepository.save(boardentity);
    }

    public BoardEntity readBoard(int id){
        Optional<BoardEntity> boardEntity = this.boardrepository.findById((long)(id));
        if(boardEntity.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return boardEntity.get();
    }

    public Iterator<BoardEntity> readBoardAll(){
        return this.boardrepository.findAll().iterator();
    }


    public void updateBoard(int id, BoardDto boarddto){
        Optional<BoardEntity> targetentity = this.boardrepository.findById(Long.valueOf(id));
        if (targetentity.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        BoardEntity boardEntity=targetentity.get();
        boardEntity.setName(boarddto.getName()==null?boardEntity.getName() : boarddto.getName());
        //업데이트로 넘어온 항목이 없으면 본래 엔티티에 있는 이름 데려오고
        //있으면 dto로 새로 넘어온 애에서 이름 데려오면 된다.

        this.boardrepository.save(boardEntity);
    }
    public void deleteBoard(int id){
        Optional<BoardEntity> targetentity = this.boardrepository.findById((long) id);
        if(targetentity.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        this.boardrepository.delete(targetentity.get());
    }

}
