package dev.yoon.board.service;

import dev.yoon.board.domain.File;
import dev.yoon.board.dto.FileDto;
import dev.yoon.board.repository.FileRepository;
import dev.yoon.board.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
// COMMIT,ROLLBACK =>
public class FileServiceImpl implements FileService{

    private final PostRepository postRepository;
    private final FileRepository fileRepository;
    @Override
    public List<File> findAllByPost(Long id) {

        List<File> fileList = this.postRepository.findAllByPost(id);
        return fileList;
    }

    @Override
    public FileDto findByFileId(Long id) {
        File file = this.fileRepository.findFilebyFileId(id);
        FileDto fileDto = FileDto.createFileDto(file);
        return fileDto;
    }

    @Override
    public void deleteFile(File file) {
        fileRepository.deleteFile(file);
    }
}
