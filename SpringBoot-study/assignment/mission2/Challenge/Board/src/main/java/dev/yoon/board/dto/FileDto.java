package dev.yoon.board.dto;

import dev.yoon.board.domain.File;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
public class FileDto {

    private String origFileName;  // 파일 원본명

    private String filePath;  // 파일 저장 경로

    private Long fileSize;


    public static FileDto createFileDto(File file) {
        FileDto fileDto = new FileDto();
        fileDto.setFilePath(file.getFilePath());
        fileDto.setFileSize(file.getFileSize());
        fileDto.setOrigFileName(file.getOrigFileName());
        return fileDto;
    }

    @Builder
    public FileDto(String origFileName, String filePath, Long fileSize) {
        this.origFileName = origFileName;
        this.filePath = filePath;
        this.fileSize = fileSize;
    }

}
