package jsbdy.mission3.controller;

import jsbdy.mission3.entity.BoardEntity;
import jsbdy.mission3.model.BoardDto;
import jsbdy.mission3.model.PostDto;
import jsbdy.mission3.repository.BoardRepository;
import jsbdy.mission3.service.BoardService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collection;
import java.util.Iterator;
import java.util.Optional;

//
@RestController
@RequestMapping("board")
public class BoardController {
    private static final Logger logger = LoggerFactory.getLogger(BoardController.class);
    private final BoardRepository boardRepository;
    private final BoardService boardService;

    public BoardController(
            BoardRepository boardRepository,
            BoardService boardService
    ){
        this.boardRepository=boardRepository;
        this.boardService=boardService;
    }
    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public void createBoard(
            @RequestBody BoardDto boardDto
    ){
        this.boardService.createBoard(boardDto);
    }
    @GetMapping("{id}")
    public BoardEntity readBoard(
            @PathVariable("id")int id
            ){
        Optional<BoardEntity> boardentity = this.boardRepository.findById((long) id);
        if(boardentity.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return boardentity.get();

    }
    @GetMapping()
    public Iterator<BoardEntity> readBoardAll(){
        return this.boardRepository.findAll().iterator();
    }


    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void updateBoard(
            @PathVariable("id")int id,
            @RequestBody BoardDto boarddto
    ){
        this.boardService.updateBoard(id, boarddto);
    }

    @DeleteMapping("{id}")
    public void deleteBoard(
            @PathVariable("id")int id
    ){
        Optional<BoardEntity> targetentity = this.boardRepository.findById((long) id);
        if(targetentity.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        this.boardRepository.delete(targetentity.get());
    }

//
//    @GetMapping("{id}")
//    public ResponseEntity<BoardDto> readBoard(
//            @PathVariable("id") Long id
//    ){
//        BoardDto dto = boardRepository.read(id);
//        return ResponseEntity.ok(dto);
//    }
//
//    @GetMapping
//    public ResponseEntity<Collection<BoardDto>> readBoardAll(){
//        return ResponseEntity.ok(this.boardRepository.readAll());
//    }
//
//    @PutMapping("{id}")
//    public ResponseEntity<?> updateBoard(
//            @PathVariable("id") Long id, @RequestBody BoardDto dto
//    ){
//        if(!boardRepository.update(id, dto)) return ResponseEntity.notFound().build();
//        return ResponseEntity.noContent().build();
//    }
//
//    @DeleteMapping("{id}")
//    public ResponseEntity<?> deleteBoard(
//            @PathVariable("id") Long id
//    ){
//        if(!boardRepository.delete(id)) return ResponseEntity.notFound().build();
//        return ResponseEntity.noContent().build();
    }

