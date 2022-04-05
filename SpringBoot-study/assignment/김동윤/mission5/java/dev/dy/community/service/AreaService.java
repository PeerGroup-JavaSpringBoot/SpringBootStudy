package dev.aquashdw.community.service;

import dev.aquashdw.community.controller.dto.AreaDto;
import dev.aquashdw.community.entity.AreaEntity;
import dev.aquashdw.community.repository.AreaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

@Service
public class AreaService {
    private static final Logger logger = LoggerFactory.getLogger(AreaService.class);
    private final AreaRepository areaRepository;
    private Object areaDto;
    private Object areaDto1;

    public AreaService(AreaRepository areaRepository) {
        this.areaRepository = areaRepository;
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
//데이터 중에서 나랑 가장 가까운 순으로 정렬~
    public List<AreaDto> readNearAreaAll(Double lat, Double lon) {
        List areaDtoList = readAreaAll();
        List<AreaDto> areaNearList = new ArrayList<>();

        Iterator<AreaDto> iter = areaDtoList.iterator();
        while (iter.hasNext()) {
            AreaDto key = iter.next();
            Double distance = Math.abs((key.getLatitude() - lat) + (key.getLongitude() - lon));
            key.setDistance(distance);
        }
        while (iter.hasNext()) {
            AreaDto key = iter.next();
            Double distance = (key.getLatitude() - lat) + (key.getLongitude() - lon);
            key.setDistance(distance);
        }
        Collections.sort(areaDtoList);
        return areaDtoList; //정렬된 아이로 주기
        }


    }
