package dev.yoon.basic_board.controller;


import dev.yoon.basic_board.domain.Board;
import dev.yoon.basic_board.dto.BoardDto;
import dev.yoon.basic_board.service.BoardService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("board")
@RequiredArgsConstructor
@Slf4j
public class BoardRestController {
    private final BoardService boardService;


    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<BoardDto> createBoard(@RequestBody @Valid BoardDto boardDto, HttpServletRequest request) {
        Board board = Board.createBoard(boardDto);
        Long id = this.boardService.createBoard(board);
        boardDto.setId(id);
        return ResponseEntity.ok(boardDto);
    }

    @GetMapping()
    public ResponseEntity<Result<List<BoardDto>>> readBoardAll() {
        List<BoardDto> boardDtos = this.boardService.readBoardAll();
        Result result = new Result(boardDtos.size(), boardDtos);
        return ResponseEntity.ok(result);
    }

    @GetMapping("{id}")
    public ResponseEntity<BoardDto> readBoardOne(@PathVariable("id") Long id) {
        return ResponseEntity.ok(this.boardService.readBoard(id));
    }


    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("{id}")
    public ResponseEntity<?> updateBoard(@PathVariable("id") Long id, @RequestBody BoardDto boardDto) {
        log.info("target id: " + id);
        log.info("update content: " + boardDto);
        if (!boardService.updateBoard(id, boardDto))
            return ResponseEntity.notFound().build();

        return ResponseEntity.noContent().build();


    }

    @ResponseStatus(HttpStatus.ACCEPTED)
    @DeleteMapping("{id}")
    public ResponseEntity<?> deletePost(@PathVariable("id") Long id) {
        if (!boardService.deleteBoard(id))
            return ResponseEntity.notFound().build();

        return ResponseEntity.noContent().build();
    }

    @Data
    @AllArgsConstructor
    static class Result<T> {
        private Integer count;
        private T data;
    }


}