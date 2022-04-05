package dev.aquashdw.community.controller;

import dev.aquashdw.community.controller.dto.AreaDto;
import dev.aquashdw.community.service.AreaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("area")
public class AreaController {
    private static final Logger logger = LoggerFactory.getLogger(AreaController.class);
    private final AreaService areaService;

    public AreaController(AreaService areaService) {
        this.areaService = areaService;
    }

//    @PostMapping
//    public ResponseEntity<AreaDto> createArea(@RequestBody AreaDto dto){
//        return ResponseEntity.ok(this.areaService.createArea(dto));
//    }

//위도 경도 넘겨주기
    @GetMapping
    public String readNearAreaAll(
            @RequestParam("lat") String lat,
            @RequestParam("lon") String lon
    ){
        Double lat1 = Double.valueOf(lat);
        Double lat2 = Double.valueOf(lon);
        List<AreaDto> areaDtoList = areaService.readNearAreaAll(lat1,lat2);

        System.out.println(areaDtoList.get(0));
        System.out.println(areaDtoList.get(1));
        System.out.println(areaDtoList.get(2));
        String result = areaDtoList.get(0).toString() +  areaDtoList.get(1).toString() + areaDtoList.get(2).toString();

      return result;
    }
//
    @GetMapping("{id}")
    public ResponseEntity<AreaDto> readArea(@PathVariable("id") Long id){
      return ResponseEntity.ok(this.areaService.readArea(id));
    }


}
