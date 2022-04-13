package dev.aquashdw.community.service;

import dev.aquashdw.community.controller.dto.AreaDto;
import dev.aquashdw.community.entity.AreaEntity;
import dev.aquashdw.community.repository.AreaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Service
public class AreaService {
    private static final Logger logger = LoggerFactory.getLogger(AreaService.class);
    private final AreaRepository areaRepository;

    public AreaService(AreaRepository areaRepository) {
        this.areaRepository = areaRepository;

        AreaEntity area1 = AreaEntity.builder()
                .latitude(37.4877)
                .longitude(127.0174)
                .regionMajor("서울시")
                .regionMinor("서초구")
                .regionPatch("서초동")
                .build();

        AreaEntity area2 = AreaEntity.builder()
                .latitude(37.4999)
                .longitude(127.0374)
                .regionMajor("서울시")
                .regionMinor("강남구")
                .regionPatch("역삼동")
                .build();

        AreaEntity area3 = AreaEntity.builder()
                .latitude(37.5140)
                .longitude(127.0565)
                .regionMajor("서울시")
                .regionMinor("강남구")
                .regionPatch("삼성동")
                .build();

        this.areaRepository.save(area1);
        this.areaRepository.save(area2);
        this.areaRepository.save(area3);


    }

    public AreaDto createArea(AreaDto areaDto) {
        AreaEntity areaEntity = new AreaEntity();
        areaEntity.setRegionMajor(areaDto.getRegionMajor());
        areaEntity.setRegionMinor(areaDto.getRegionMinor());
        areaEntity.setRegionPatch(areaDto.getRegionPatch());
        areaEntity.setLatitude(areaDto.getLatitude());
        areaEntity.setLongitude(areaDto.getLongitude());
        areaEntity = areaRepository.save(areaEntity);

        return new AreaDto(areaEntity);
    }

    public AreaDto readArea(Long id) {
        Optional<AreaEntity> areaEntityOptional = areaRepository.findById(id);
        if (areaEntityOptional.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        return new AreaDto(areaEntityOptional.get());
    }

    public List<AreaDto> readAreaAll() {
        List<AreaDto> areaDtoList = new ArrayList<>();
        areaRepository.findAll().forEach(areaEntity -> areaDtoList.add(new AreaDto(areaEntity)));
        return areaDtoList;
    }

    public AreaDto findNearestArea(Double latitude, Double longitude) {

        List<AreaEntity> all = areaRepository.findAll();
        AreaEntity areaEntity = getNearestArea(all, latitude, longitude);

        return AreaDto.toDto(areaEntity);

    }

    private AreaEntity getNearestArea(List<AreaEntity> all, Double latitude, Double longitude ) {

        Double min = Double.MAX_VALUE;
        AreaEntity result = new AreaEntity();

        for (AreaEntity area : all) {
            Double tmp = latitude - area.getLatitude() + longitude - area.getLongitude();
            if(min > tmp) {
                min = tmp;
                result = area;
            }
        }
        return result;
    }


}
