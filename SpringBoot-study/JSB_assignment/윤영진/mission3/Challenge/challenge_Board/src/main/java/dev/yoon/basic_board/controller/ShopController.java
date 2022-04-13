package dev.yoon.basic_board.controller;

import dev.yoon.basic_board.dto.Result;
import dev.yoon.basic_board.dto.shop.ShopDto;
import dev.yoon.basic_board.service.ShopService;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("shop")
@RequiredArgsConstructor
public class ShopController {

    private final ShopService shopService;


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<ShopDto> creatShop(
            @RequestBody ShopDto shopDto) {
        ShopDto dto = this.shopService.createShop(shopDto);

        if(dto == null) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(dto);

    }

    @GetMapping()
    public ResponseEntity<Result<List<ShopDto>>> readShopAll(
    ) {
        List<ShopDto> shopDtos = this.shopService.readShopAll();
        Result result = new Result(shopDtos.size(),shopDtos);

        if (shopDtos == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(result);
    }

    @GetMapping("{shopId}")
    public ResponseEntity<ShopDto> readShopOne(
            @PathVariable("shopId") Long shopId
    ) {
        ShopDto shopDto = this.shopService.readShopOne(shopId);

        if (shopDto == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(shopDto);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("{shopId}")
    public ResponseEntity<?> updateShop(
            @PathVariable("shopId") Long shopId,
            @RequestBody ShopDto shopDto) {

        if (!shopService.updateShop(shopId, shopDto))
            return ResponseEntity.notFound().build();

        return ResponseEntity.noContent().build();
    }

    @ResponseStatus(HttpStatus.ACCEPTED)
    @DeleteMapping("{shopId}")
    public ResponseEntity<?> deleteShop(
            @PathVariable("shopId") Long shopId) {

        if (!this.shopService.deleteShop(shopId))
            return ResponseEntity.notFound().build();

        return ResponseEntity.noContent().build();
    }


}
