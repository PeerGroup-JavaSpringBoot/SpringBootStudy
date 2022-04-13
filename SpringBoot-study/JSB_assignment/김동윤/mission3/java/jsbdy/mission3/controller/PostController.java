package jsbdy.mission3.controller;

import jsbdy.mission3.model.PostDto;
import jsbdy.mission3.repository.PostRepository;
import jsbdy.mission3.service.PostService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("board/{boardId}/post")
public class PostController {
    private static final Logger logger = LoggerFactory.getLogger(PostController.class);
    private final PostService postservice;
    private Long userid;
    private Long boardid;

    public PostController(
            @Autowired PostService postservice

//            @PathVariable("boardId") Long boardId 얘는 빈이 아니니깐 여따 두면 에러남..
    )      {
        this.postservice = postservice;
            this.userid=null;
            this.boardid=null;
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public void createPost(
            @RequestHeader("user") Long userid,
            @PathVariable("boardId") Long boardid,
            @RequestBody PostDto postdto
    ){
        logger.info(String.valueOf(userid));
        this.postservice.createPost(postdto,userid,boardid);
    }

    @GetMapping("{id}")
    public PostDto readPost(
            @PathVariable("id")int id
    ){
        return this.postservice.readPost(id);
    }

    @GetMapping("")
    public List<PostDto > readPostAll(){
        return this.postservice.readPostAll();
    }

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void updatePost(
            @PathVariable("id")int id,
            @RequestBody PostDto postdto
    ){
        this.postservice.updatePost(id, postdto);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void deletePost(
            @PathVariable("id") int id
    ){
        this.postservice.deletePost(id);
    }
}

//
//@RestController
//@RequestMapping("board/{boardId}/post")
//public class PostController {
//    private static Logger logger = LoggerFactory.getLogger(PostController.class);
//    private final PostRepository postRepository;
//
//
//    public PostController(PostRepository postRepository){
//        this.postRepository = postRepository; //레포지토리 구현 후 추가
//    }
//
//    @PostMapping
//    public ResponseEntity<PostDto> createPost(
//            @PathVariable("boardId") Long boardId,
//            @RequestBody PostDto dto
//    ){
//        PostDto result = this.postRepository.create(boardId,dto);
//        return ResponseEntity.ok(result.passwordMasked());
//        //비밀번호 안보임 처리후 객체 돌려주는 메소드
//    }
//
//    @GetMapping("{postId}")
//    public ResponseEntity<PostDto> readPost(
//            @PathVariable("boardId") Long boardId,
//            @PathVariable("postId") Long postId
//    ){
//        PostDto postDto = this.postRepository.read(boardId, postId);
//        if(postDto==null) return ResponseEntity.notFound().build();
//        else return ResponseEntity.ok(postDto.passwordMasked());
//
//    }
//
//    @GetMapping
//    public ResponseEntity<Collection<PostDto>> readPostAll(
//            @PathVariable("boardId") Long boardId
//    ){
//        Collection<PostDto> postList = this.postRepository.readAll(boardId);
//        if (postList==null) return ResponseEntity.notFound().build();
//        else return ResponseEntity.ok(postList);
//    }
//
//    @PutMapping("{postId}")
//    public ResponseEntity<?> updatePost(
//            @PathVariable("boardId") Long boardId,
//            @PathVariable("postId") Long postId,
//            @RequestBody PostDto dto
//    ){
//        if(!postRepository.update(boardId, postId, dto))
//            return ResponseEntity.notFound().build();
//        return ResponseEntity.noContent().build();
//    }
//
//    @DeleteMapping("{postId}")
//    public ResponseEntity<?> deletePost(
//            @PathVariable("boardId") Long boardId,
//            @PathVariable("postId") Long postId,
//            @RequestParam("password") String password
//            //
//    ){
//        if(!postRepository.delete(boardId, postId, password))
//            return ResponseEntity.notFound().build();
//        return ResponseEntity.noContent().build();
//    }
//
//}
