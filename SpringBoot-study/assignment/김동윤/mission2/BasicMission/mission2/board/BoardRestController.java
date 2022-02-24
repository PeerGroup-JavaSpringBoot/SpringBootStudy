package jsbdy.mission2.board;


import jsbdy.mission2.board.BoardDto;
import jsbdy.mission2.board.BoardService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("board")
public class BoardRestController {
    private static final Logger logger = LoggerFactory.getLogger(jsbdy.mission2.board.BoardRestController.class);
    private final BoardService boardService;
    public BoardRestController(
            @Autowired BoardService boardService
    ){
        this.boardService = boardService;
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public void createBoard(@RequestBody BoardDto boardDto, HttpServletRequest request){
        logger.info("content");
        logger.info(boardDto.toString());
        logger.info("full request");
        logger.info(String.valueOf(request));
        logger.info("method of request");
        logger.info(request.getMethod());
        this.boardService.createBoard(boardDto);
    }

    @GetMapping()
    public List<BoardDto> readBoardAll(){
        logger.info("read-all");
        return this.boardService.readBoardAll();
    }

    //GET
    //GET /post?id=0

    @GetMapping("{id}")
    public BoardDto readBoard(@PathVariable("id") int id){
        logger.info("in read Board");
        logger.info(String.valueOf(id));
        return this.boardService.readBoard(id);
    }

    @PutMapping("{id}")
    public void updateBoard(
            @PathVariable("id") int id,
            @RequestBody  BoardDto BoardDto
    ){
        BoardDto targetBoard = this.boardService.readBoard(id); //업데이트를 위한 목적 타겟
        if (BoardDto.getBoardname()!=null){
            targetBoard.setBoardname(BoardDto.getBoardname());
        }
        this.boardService.updateBoard(id, targetBoard);
    }

    @DeleteMapping("{id}")
    public void deleteBoard(@PathVariable("{id}") int id){
        this.boardService.deleteBoard(id);
    }
}
