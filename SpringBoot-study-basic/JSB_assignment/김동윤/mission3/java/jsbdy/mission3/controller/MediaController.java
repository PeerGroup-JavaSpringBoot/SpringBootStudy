package jsbdy.mission3.controller;

import jsbdy.mission3.model.MediaDescriptorDto;
import jsbdy.mission3.service.MedialService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;

@RestController
@RequestMapping("media")
public class MediaController {
    private static final Logger logger = LoggerFactory.getLogger(MediaController.class);
    private final MedialService medialService;

    public MediaController(MedialService  medialService){
        this.medialService=medialService;
    }

    @PostMapping("upload")
    public ResponseEntity<MediaDescriptorDto> uploadMedia(
            @RequestParam("file") MultipartFile file
    ){
        MediaDescriptorDto dto = this.medialService.saveFile(file);
        return ResponseEntity
                .status(dto.getStatus())
                .body(dto);
    }

    @PostMapping("upload-bulk")
    public ResponseEntity<Collection<MediaDescriptorDto>> uploadMediaBulk(
            @RequestParam("file") MultipartFile[] files
            //이름 동일한게 여러개 들어오면 알아서 배열로 인식
    ){
        return ResponseEntity
                .status(HttpStatus.MULTI_STATUS)
                .body(this.medialService.saveFileBulk(files));
    }

    @GetMapping("**")
    public ResponseEntity<byte[]> staticLikeEndpoint(
            HttpServletRequest request){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_PNG);
        //미디어타입과 같은 확장자 정해줄 때 잘 정해줘야 함
        return new ResponseEntity<>(
                this.medialService.getFileAsBytes(request.getRequestURI().split("media")[1]),
                headers,
                HttpStatus.OK
                );
    }
}
