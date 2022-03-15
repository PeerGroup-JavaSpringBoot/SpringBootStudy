package dev.yoon.basic_board.repository;

import dev.yoon.basic_board.domain.Board;
import dev.yoon.basic_board.dto.BoardDto;

import java.util.List;

public interface BoardRepository {

    void save(Board board);

    List<Board> findAll();

    Board findOne(Long id);

    List<Board> findByTitle(String title);

    boolean updateBoard(Long id, BoardDto dto);

    void delete(Board board);

}
