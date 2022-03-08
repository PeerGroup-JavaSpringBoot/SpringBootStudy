package jsbdy.mission3.service;

import jsbdy.mission3.model.MediaDescriptorDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
@Service
public class LocalMediaService implements MedialService{

    private static final Logger logger = LoggerFactory.getLogger(LocalMediaService.class);
    //저장 경로 위해 추가
    private final String basePath = "./media";

    @Override
    public MediaDescriptorDto saveFile(MultipartFile file) {
        //미디어 파일이 하나만 들어오는 경우
        return this.saveToDir(file);
    }

    @Override
    public Collection<MediaDescriptorDto> saveFileBulk(MultipartFile[] files) {
        //미디어 파일이 다수로 들어오는 경우
        Collection<MediaDescriptorDto> resultList = new ArrayList<>();
        for(MultipartFile file : files){
            resultList.add(this.saveToDir(file));
        }
        return resultList;
    }

    @Override
    public byte[] getFileAsBytes(String resourcePath) {
        try{
            return Files.readAllBytes(Path.of(basePath, resourcePath));
        } catch (IOException e) {
            e.printStackTrace();
            //읽는 과정에서 에러
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    private MediaDescriptorDto saveToDir(MultipartFile file){
        MediaDescriptorDto dto = new MediaDescriptorDto();
        dto.setStatus(200);
        dto.setOriginalName(file.getOriginalFilename());

        try{
            LocalDateTime now = LocalDateTime.now();
            //날짜별로 파일을 나누기 위해 시간 받아오기
            //now로 디렉토리를 생성하고, 거기에다 파일 저장할 것
            String targetDir = Path.of(
                    basePath,
                    now.format(DateTimeFormatter.ISO_DATE)
            ).toString();
            logger.info("ok1");
            //현재 시간을 어떤 형식으로 나타내줄지 정하는 것
            String newFileName = now.format(DateTimeFormatter.ofPattern("HHmmss"))
                    + "_"
                    //업로드한 파일의 이름을 구한다.
                    + file.getOriginalFilename();
            logger.info("ok2");
            File dirNow = new File(targetDir);
            logger.info("ok3");
            if(!dirNow.exists()) dirNow.mkdir();//디렉토리 없으면 만들기
            logger.info("ok4");

            //업로드한 파일 데이터를 지정한 파일에 저장
            // MultipartFile.transferTo() 메서드를 사용
            file.transferTo(Path.of(
                    targetDir,
                    newFileName
            ));
            logger.info("ok5");

            dto.setResourcePath(Path.of(
                    targetDir,
                    newFileName
            ).toString().substring(1));

            logger.info("ok6");

            return dto;

        } catch (IOException e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            dto.setMessage("failed");
            dto.setStatus(500);
            return dto;
        }

    }
}
