package dev.yoon.challenge_community.service;

import dev.yoon.challenge_community.domain.Address;
import dev.yoon.challenge_community.domain.Area;
import dev.yoon.challenge_community.domain.Location;
import dev.yoon.challenge_community.repository.AreaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AreaService {

    private final AreaRepository areaRepository;

    private AreaService (
            @Autowired AreaRepository areaRepository
    ){
        this.areaRepository = areaRepository;

        Address address = Address.builder()
                .province("서울시")
                .city("서초구")
                .street("서초동")
                .build();

        Location location = Location.builder()
                .latitude(37.4877)
                .longtitude(127.0174)
                .build();

        Area area = Area.builder()
                .address(address)
                .location(location)
                .build();

        Address address1 = Address.builder()
                .province("서울시")
                .city("강남구")
                .street("역삼동")
                .build();

        Location location1 = Location.builder()
                .latitude(37.4999)
                .longtitude(127.0374)
                .build();

        Area area1 = Area.builder()
                .address(address1)
                .location(location1)
                .build();
        Address address2 = Address.builder()
                .province("서울시")
                .city("강남구")
                .street("삼성동")
                .build();

        Location location2 = Location.builder()
                .latitude(37.5140)
                .longtitude(127.0565)
                .build();

        Area area2 = Area.builder()
                .address(address2)
                .location(location2)
                .build();

        this.areaRepository.save(area);
        this.areaRepository.save(area1);
        this.areaRepository.save(area2);



    }
}
