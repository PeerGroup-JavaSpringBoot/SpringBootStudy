package eci.server.ItemModule.dto.item;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.PositiveOrZero;
import java.util.ArrayList;
import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemUpdateRequest {

    //@NotBlank(message = "아이템 이름을 입력해주세요.")
    private String name;

    //@NotBlank(message = "아이템 타입을 입력해주세요.")
    private String type;

    //@NotNull(message = "너비를 입력해주세요.")
    private String width;

    //@NotNull(message = "높이를 입력해주세요.")
    //@PositiveOrZero(message = "0 이상을 입력해주세요")
    private String height;

    //@NotNull(message = "무게를 입력해주세요.")
    //@PositiveOrZero(message = "0 이상을 입력해주세요")
    private String weight;


    /**
     * 추가된 이미지를 첨부
     */
    private List<MultipartFile> addedImages = new ArrayList<>();

    /**
     * 추가된 파일을 첨부
     */
    private List<MultipartFile> addedAttachments = new ArrayList<>();
    private List<String> addedTag = new ArrayList<>();
    private List<String> addedAttachmentComment = new ArrayList<>();

    /**
     * 색깔 입력
     */
    //@NotNull(message = "색깔을 입력해주세요.")
    //@PositiveOrZero(message = "유효한 색깔 아이디를 입력해주세요")
    private Long colorId;

    private List<Long> materials = new ArrayList<>();

    private List<Long> manufactures = new ArrayList<>();

    private List<String> partnumbers = new ArrayList<>();
    /**
     * 삭제될 사진 아이디 입력 - 실제 삭제 예정
     */
    private List<Long> deletedImages = new ArrayList<>();
    /**
     * 삭제될 파일 아이디 입력 - is deleted 만 true
     */
    private List<Long> deletedAttachments = new ArrayList<>();

    @Null
    private Long modifierId; //05-22
}


