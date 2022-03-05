package dev.yoon.board.controller;

import dev.yoon.board.domain.File;
import dev.yoon.board.dto.FileDto;
import dev.yoon.board.dto.PostDto;
import dev.yoon.board.service.FileService;
import dev.yoon.board.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("post")
@RequiredArgsConstructor
public class PostRestController {

    private final PostService postService;
    private final FileService fileService;


    @PostMapping(value = "{boardId}")
    @ResponseStatus(HttpStatus.CREATED)
    public void createPost(
            @PathVariable("boardId") Long id,
            @RequestPart(value = "file", required = false) List<MultipartFile> files,
            @RequestPart(value = "post") PostDto postDto,
            HttpServletRequest request) throws Exception {

        postDto.setBoardId(id);
//        log.info(request.getHeader("Content-Type"));
        this.postService.createPost(postDto, files);
    }

    @GetMapping()
    public List<PostDto> readPostAll() {
        log.info("in read post all");
        List<PostDto> postDtos = this.postService.readPostAll();
        return postDtos;
    }

    @GetMapping("{id}")
    public PostDto readPostOne(@PathVariable("id") Long id) {
        log.info("in read post one");
        return this.postService.readPostOne(id);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("{id}")
    public void updatePost(
            @PathVariable("id") Long id,
            @RequestPart(value = "file", required = false) List<MultipartFile> files,
            @RequestPart(value = "post") PostDto postDto) {
        log.info("target id: " + id);
        log.info("update content: " + postDto);

        List<File> fileList = fileService.findAllByPost(id);

        List<MultipartFile> multipartFileList = files;

        List<MultipartFile> addFileList = new ArrayList<>();

        // 기존 Post에 File 없던 경우
        if (CollectionUtils.isEmpty(fileList)) {
            if (!CollectionUtils.isEmpty(multipartFileList)) {
                for (MultipartFile multipartFile : multipartFileList) {
                    addFileList.add(multipartFile);
                }
            }
        }
        // 기존 Post에 File 있던 경우
        else {
            // 전달된 파일 없는 경우
            if (CollectionUtils.isEmpty(multipartFileList)) {
                for (File file : fileList)
                    fileService.deleteFile(file);
            }
            // 전달된 파일 있는 경우
            else {
                List<String> dbOriginNameList = new ArrayList<>();

                // DB의 파일 원본명 추출
                for (File file : fileList) {

                    FileDto fileDto = fileService.findByFileId(file.getId());

                    String dbOrigFileName = fileDto.getOrigFileName();

                    // 서버에 저장된 파일들 중 전달되어온 파일이 존재 x 경우
                    if (!multipartFileList.contains(dbOrigFileName))
                        fileService.deleteFile(file);
                    else
                        // 기존에 있던 파일인 경우
                        dbOriginNameList.add(dbOrigFileName);
                }

                for (MultipartFile multipartFile : multipartFileList) {

                    String multipartOrigName = multipartFile.getOriginalFilename();
                    // DB에 없는 파일
                    if (!dbOriginNameList.contains(multipartOrigName)) {
                        addFileList.add(multipartFile);
                    }
                }
            }


        }
        this.postService.updatePost(id, postDto, addFileList);

    }

    @ResponseStatus(HttpStatus.ACCEPTED)
    @DeleteMapping("{id}")
    public void deletePost(@PathVariable("id") Long id, @RequestBody PostDto postDto) {
        this.postService.deletePost(id, postDto.getPw());
    }

}
