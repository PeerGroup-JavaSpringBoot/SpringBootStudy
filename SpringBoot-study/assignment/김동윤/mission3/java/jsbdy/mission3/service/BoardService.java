package jsbdy.mission3.service;


import jsbdy.mission3.dao.BoardDao;
import jsbdy.mission3.dao.PostDao;
import jsbdy.mission3.entity.BoardEntity;
import jsbdy.mission3.entity.PostEntity;
import jsbdy.mission3.model.BoardDto;
import jsbdy.mission3.model.PostDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class BoardService{
    private static final Logger logger = LoggerFactory.getLogger(PostService.class);
    private final BoardDao boarddao; //postdao 추가 선언 (활용 위해)

    public BoardService(@Autowired BoardDao boarddao){
        this.boarddao = boarddao; //postdao 초기화
    }

    public void createBoard(BoardDto boarddto){
        this.boarddao.createBoard(boarddto);
    }

    public BoardDto readBoard(int id){
        BoardEntity boardEntity = this.boarddao.readBoard(id);
        return new BoardDto(
                boardEntity.getId(),
                boardEntity.getName()==null?null:(boardEntity.getName())
        );
    }

    public List<BoardDto> readBoardAll(){
        Iterator<BoardEntity> iterator=this.boarddao.readBoardAll();
        List<BoardDto> boardDtoList = new ArrayList<>();

        while(iterator.hasNext()){
            BoardEntity boardEntity = iterator.next();
            boardDtoList.add(new BoardDto(
                    boardEntity.getId(),
                    boardEntity.getName()
            ));
        }
        return boardDtoList;
    }

    public void updateBoard(int id, BoardDto boardDto){
        this.boarddao.updateBoard(id,boardDto);
    }

    public void deleteBoard(int id){
        this.boarddao.deleteBoard(id);
    }
}