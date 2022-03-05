package dev.yoon.board.service;

import dev.yoon.board.domain.File;
import dev.yoon.board.dto.FileDto;

import java.util.List;

public interface FileService {

    List<File> findAllByPost(Long id);

    FileDto findByFileId(Long id);

    void deleteFile(File file);

}
