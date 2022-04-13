package jsbdy.mission2.board;

import jsbdy.mission2.post.PostDto;

import java.util.List;

public interface BoardRepositoryi {
    boolean save(BoardDto boarddto);
    BoardDto findById(int id);
    List<BoardDto> findAll();
    boolean update(int id, BoardDto boarddto);
    boolean delete(int id);
}
