package dev.yoon.board.service;

import dev.yoon.board.domain.Board;
import dev.yoon.board.repository.BoardRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class BoardServiceImplTest {

    @Autowired
    BoardService boardService;
    @Autowired
    BoardRepository boardRepository;


    @Test
    @Rollback(value = false)
    public void PostServiceImplTest() throws Exception {

        //given
        Board board = new Board();
        board.setName("test1");
        Board posts = new Board();
        posts.setName("test2");

        //when
//        Long post1 = boardService.createPost(board);
//        Long post2 = boardService.createPost(posts);

//        System.out.println(post1);
//        System.out.println(post2);

        //then

    }

}