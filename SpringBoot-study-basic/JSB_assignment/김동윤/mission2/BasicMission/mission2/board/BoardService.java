package jsbdy.mission2.board;

import jsbdy.mission2.post.PostDto;
import jsbdy.mission2.post.PostRepositoryi;
import jsbdy.mission2.post.PostService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class BoardService implements BoardServicei{

    private static final Logger logger = LoggerFactory.getLogger(BoardService.class);

    private final BoardRepositoryi boardRepository;

    public BoardService(
            @Autowired BoardRepositoryi boardRepository
    ) {
        this.boardRepository = boardRepository;
    }


    @Override
    public List<BoardDto> readBoardAll() {
        return this.boardRepository.findAll();
    }

    @Override
    public void createBoard(BoardDto dto) {
        if(!this.boardRepository.save(dto)){
            throw new RuntimeException(("save failed"));
        }
//        this.postRepository.save(dto);
    }
    @Override
    public BoardDto readBoard(int id) {
        return this.boardRepository.findById(id);
    }

    @Override
    public void updateBoard(int id, BoardDto dto) {
        this.boardRepository.update(id,dto);

    }

    @Override
    public void deleteBoard(int id) {
        this.boardRepository.delete(id);
    }
}
