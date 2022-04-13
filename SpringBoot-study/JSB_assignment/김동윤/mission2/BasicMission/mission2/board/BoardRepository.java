package jsbdy.mission2.board;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
@Repository
public class BoardRepository implements BoardRepositoryi{
    private static final Logger logger = LoggerFactory.getLogger(BoardRepository.class);
    private final List<BoardDto> boardList;

    public BoardRepository(List<BoardDto> boardList) {
        this.boardList = boardList;
    }

    public BoardRepository(){
        this.boardList = new ArrayList<>();
    }

    @Override
    public boolean save(BoardDto dto) {
        return this.boardList.add(dto);
        //-> add가 애초에 boolean값을 돌려준
        //return true;
    }

    @Override
    public BoardDto findById(int id) {
        return this.boardList.get(id);
    }

    @Override
    public List<BoardDto> findAll() {
        return this.boardList;
    }

    @Override
    public boolean update(int id, BoardDto dto) {
        BoardDto targetPost = this.boardList.get(id); //업데이트를 위한 목적 타겟
        if (dto.getBoardname()!=null){
            targetPost.setBoardname(dto.getBoardname());
        }
        this.boardList.set(id, targetPost);

        return true;
    }
    //return 값은 성공 여부 알려주는 것
    @Override
    public boolean delete(int id) {
        this.boardList.remove(id);
        return true;
    }
}
