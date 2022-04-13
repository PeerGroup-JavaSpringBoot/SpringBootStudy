package dev.yoon.board.service;

import dev.yoon.board.domain.Board;
import dev.yoon.board.dto.BoardDto;
import dev.yoon.board.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class BoardServiceImpl implements BoardService {

    private final BoardRepository boardRepository;

    @Override
    public Long createBoard(Board board) {
        boardRepository.save(board);
        return board.getId();
    }

    @Override
    public List<BoardDto> readBoardAll() {
        List<Board> boards = this.boardRepository.findAll();
        List<BoardDto> boardDtos = new ArrayList<>();
        for(Board board : boards) {
            BoardDto boardDto = BoardDto.createBoardDto(board);
            boardDtos.add(boardDto);
        }
        return boardDtos;
    }

    @Override
    public BoardDto readBoard(Long id) {
        Board board = this.boardRepository.findOne(id);
        return BoardDto.createBoardDto(board);
    }

    @Override
    public void updateBoard(Long id, BoardDto boardDto) {
        Board board = boardRepository.findOne(id);
        board.setName(boardDto.getName());
    }
    @Override
    public void deleteBoard(Long id) {
        Board board = this.boardRepository.findOne(id);
        this.boardRepository.delete(board);
    }
}
