package dev.yoon.basic_board.service;


import dev.yoon.basic_board.domain.Board;
import dev.yoon.basic_board.dto.BoardDto;

import java.util.List;

/**
 * 목적
 * controller(endpoint)와 model(database) 비즈니스로직을 분리하는 것
 * 데이터 회수는 repo, 회수된 데이터를 검증하는 과정 service
 */
public interface BoardService {

    Long createBoard(Board board);

    List<BoardDto> readBoardAll();

    BoardDto readBoard(Long id);

    boolean updateBoard(Long id, BoardDto boardDto);

    boolean deleteBoard(Long id);


}
