package dev.yoon.basic_board.service;

import dev.yoon.basic_board.domain.Board;
import dev.yoon.basic_board.domain.Post;
import dev.yoon.basic_board.dto.PostDto;
import dev.yoon.basic_board.repository.BoardRepository;
import dev.yoon.basic_board.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final BoardRepository boardRepository;


    @Override
    public void createPost(PostDto postDto) throws Exception {

        Board board = this.boardRepository.findOne(postDto.getBoardId());

        Post post = Post.createPost(postDto);

        board.addPost(post);

        this.postRepository.save(post);
    }

    @Override
    public List<PostDto> readPostAll() {

        List<Post> postList = this.postRepository.findAll();

        List<PostDto> postDtos = new ArrayList<>();

        for (Post post : postList) {

            PostDto postDto = PostDto.createPostDto(post);
            postDtos.add(postDto);
        }
        return postDtos;

    }

    @Override
    public PostDto readPostOne(Long id) {
        Post post = this.postRepository.findById(id);

        PostDto postDto = PostDto.createPostDto(post);
        return postDto;
    }

    @SneakyThrows
    @Override
    public void updatePost(Long id, PostDto postDto) {

        Post post = this.postRepository.findById(id);
        post.update(postDto);

    }

    @Override
    public void deletePost(Long id, String pw) {
        Post post = this.postRepository.findById(id);
        // 전달된 비밀번호가 동일한 경우 삭제
        if(post.getPw().equals(pw)) {
            this.postRepository.delete(post);
        }

    }
}
