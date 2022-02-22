package dev.yoon.board.domain;

import dev.yoon.board.dto.FileDto;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
public class File {

    @Id
    @GeneratedValue
    @Column(name = "file_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    @Column(nullable = false)
    private String origFileName;  // 파일 원본명

    @Column(nullable = false)
    private String filePath;  // 파일 저장 경로

    private Long fileSize;

    @Builder
    public File(String origFileName, String filePath, Long fileSize) {
        this.origFileName = origFileName;
        this.filePath = filePath;
        this.fileSize = fileSize;
    }

    public static File createFile(FileDto fileDto) {
        File file = new File();
        file.setFilePath(fileDto.getFilePath());
        file.setOrigFileName(fileDto.getOrigFileName());
        file.setFilePath(fileDto.getFilePath());
        return file;
    }


}
