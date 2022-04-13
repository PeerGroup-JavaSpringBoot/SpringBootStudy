package jsbdy.mission3.repository;

import jsbdy.mission3.entity.BoardEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface BoardRepository extends CrudRepository<BoardEntity, Long> {

//    BoardDto create(BoardDto dto);
//    BoardDto read(Long id);
//    Collection<BoardDto> readAll();
//    boolean update(Long id, BoardDto dto);
//    boolean delete(Long id);
}
