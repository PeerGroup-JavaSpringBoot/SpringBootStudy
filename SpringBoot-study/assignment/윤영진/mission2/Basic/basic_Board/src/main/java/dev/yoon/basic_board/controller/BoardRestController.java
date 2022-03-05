package dev.yoon.basic_board.controller;


import dev.yoon.basic_board.domain.Board;
import dev.yoon.basic_board.dto.BoardDto;
import dev.yoon.basic_board.service.BoardService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("board")
@Slf4j
public class BoardRestController {
    private final BoardService boardService;

    public BoardRestController(
            @Autowired BoardService boardService
    ) {
        this.boardService = boardService;
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public void createBoard(@RequestBody BoardDto boardDto, HttpServletRequest request) {
//        log.info(request.getHeader("Content-Type"));
        Board board = Board.createBoard(boardDto);
        this.boardService.createBoard(board);
    }

    @GetMapping()
    public List<BoardDto> readBoardAll() {
        log.info("in read Board all");
        return this.boardService.readBoardAll();
    }

    @GetMapping("{id}")
    public BoardDto readBoardOne(@PathVariable("id") Long id) {
        log.info("in read Board one");
        return this.boardService.readBoard(id);
    }


    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("{id}")
    public void updateBoard(@PathVariable("id") Long id, @RequestBody BoardDto boardDto) {
        log.info("target id: " + id);
        log.info("update content: " + boardDto);
        boardService.updateBoard(id, boardDto);
    }

    @ResponseStatus(HttpStatus.ACCEPTED)
    @DeleteMapping("{id}")
    public void deletePost(@PathVariable("id") Long id) {
        this.boardService.deleteBoard(id);
    }


}
