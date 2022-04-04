package dev.aquashdw.community.controller.dto;

import dev.aquashdw.community.entity.AreaEntity;
import lombok.*;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class AreaDto {
    private Long id;
    private String regionMajor;
    private String regionMinor;
    private String regionPatch;
    private Double latitude;
    private Double longitude;


    public AreaDto(AreaEntity areaEntity){
        this.id = areaEntity.getId();
        this.regionMajor = areaEntity.getRegionMajor();
        this.regionMinor = areaEntity.getRegionMinor();
        this.regionPatch = areaEntity.getRegionPatch();
        this.latitude = areaEntity.getLatitude();
        this.longitude = areaEntity.getLongitude();
    }


    public static AreaDto toDto(AreaEntity areaEntity) {

        return AreaDto.builder()
                .latitude(areaEntity.getLatitude())
                .longitude(areaEntity.getLongitude())
                .regionMajor(areaEntity.getRegionMajor())
                .regionMinor(areaEntity.getRegionMinor())
                .regionPatch(areaEntity.getRegionPatch())
                .build();
    }


}
