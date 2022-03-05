package dev.yoon.board.repository;
import dev.yoon.board.domain.Board;

import java.util.List;

public interface BoardRepository {

    void save(Board board);

    List<Board> findAll();

    Board findOne(Long id);

    List<Board> findByTitle(String title);

    void delete(Board board);

}
