package dev.yoon.board.repository;

import dev.yoon.board.domain.File;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FileRepository {

    void save(File file);

    List<File> findFilebyPostId(Long id);

    void deleteFile(File file);


    File findFilebyFileId(Long id);
}
