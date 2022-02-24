package jsbdy.mission2.post;

import jsbdy.mission2.board.BoardService;
import jsbdy.mission2.board.BoardServicei;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("post")
public class PostRestController {
    private static final Logger logger = LoggerFactory.getLogger(PostRestController.class);
    private final PostService postservice;
    private final BoardService boardService;
    public PostRestController(
            @Autowired PostService postservice, BoardService boardService
    ){
        this.postservice = postservice;
        this.boardService = boardService;
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public void createPost(@RequestBody PostDto postDto, HttpServletRequest request){
        this.postservice.createPost(postDto);
    }

    @GetMapping()
    public List<PostDto> readPostAll(){
        logger.info("read-all");
        return this.postservice.readPostAll();
    }

    //GET
    //GET /post?id=0

    @GetMapping("{board}/{id}")
    public PostDto readPost(@PathVariable("id") int id, @PathVariable("board") int board){
        return this.postservice.readPost(id, board);
    }

    @PutMapping("{board}/{id}")
    public void updatePost(
            @PathVariable("id") int id,
            @PathVariable("board") int board,
            @RequestBody  PostDto postDto
    ){
        PostDto targetPost = this.postservice.readPost(id, board); //업데이트를 위한 목적 타겟
        if (postDto.getTitle()!=null){
            targetPost.setTitle(postDto.getTitle());
        }
        if (postDto.getContent()!=null){
            targetPost.setContent(postDto.getContent());
        }
        this.postservice.updatePost(id, targetPost);
    }

    @DeleteMapping("{id}/{pw}")
    public void deletePost(
            @PathVariable("id") int id,
            @PathVariable("pw") String pw
    ){
        this.postservice.deletePost(id , pw);
    }
}
