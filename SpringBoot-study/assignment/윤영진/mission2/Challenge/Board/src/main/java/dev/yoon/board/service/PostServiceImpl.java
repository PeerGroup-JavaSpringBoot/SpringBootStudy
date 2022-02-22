package dev.yoon.board.service;

import dev.yoon.board.FileHandler;
import dev.yoon.board.domain.File;
import dev.yoon.board.domain.Post;
import dev.yoon.board.domain.Board;
import dev.yoon.board.dto.PostDto;
import dev.yoon.board.repository.FileRepository;
import dev.yoon.board.repository.PostRepository;
import dev.yoon.board.repository.BoardRepository;
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
    private final FileRepository fileRepository;
    private final FileHandler fileHandler;

    @Override
    public void createPost(PostDto postDto, List<MultipartFile> files) throws Exception {

        Board board = this.boardRepository.findOne(postDto.getBoardId());

        Post post = Post.createPost(postDto);

        board.addPost(post);

        List<File> fileList = fileHandler.parseFileInfo(files);

        if(!fileList.isEmpty()) {
            for(File file : fileList) {
                // 파일을 DB에 저장
                fileRepository.save(file);
                post.addFile(file);
            }
        }
        this.postRepository.save(post);
    }

    @Override
    public List<PostDto> readPostAll() {
        List<Post> postList = this.postRepository.findAll();

        List<PostDto> postDtos = new ArrayList<>();

        for (Post post : postList) {
//            List<File> fileList = this.fileRepository.findFilebyPostId(post.getId());

            PostDto postDto = PostDto.createPostDto(post);
//            postDto.addOrigFileNames(fileList);
            postDtos.add(postDto);
        }
        return postDtos;

    }

    @Override
    public PostDto readPostOne(Long id) {
        Post post = this.postRepository.findById(id);
//        List<File> fileList = this.fileRepository.findFilebyPostId(post.getId());

        PostDto postDto = PostDto.createPostDto(post);
//        postDto.addOrigFileNames(fileList);
        return postDto;
    }

    @SneakyThrows
    @Override
    public void updatePost(Long id, PostDto postDto, List<MultipartFile> multipartFileList) {

        List<File> fileList = fileHandler.parseFileInfo(multipartFileList);
        Post post = postRepository.findById(id);

        if(!fileList.isEmpty()) {
            for (File file : fileList) {
                file.setPost(post);
                fileRepository.save(file);
            }
        }
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
