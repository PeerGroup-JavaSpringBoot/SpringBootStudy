package dev.yoon.challenge_community.controller;


import dev.yoon.challenge_community.dto.common.Result;
import dev.yoon.challenge_community.dto.shop.ShopDto;
import dev.yoon.challenge_community.service.ShopService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("shop")
@RequiredArgsConstructor
public class ShopController {

    private final ShopService shopService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<ShopDto.Res> creatShop(
            @RequestBody @Valid ShopDto.Req shopDto) {

        return ResponseEntity.ok(this.shopService.createShop(shopDto));

    }

    @GetMapping()
    public ResponseEntity<Result<List<ShopDto.Res>>> readShopAll(
    ) {
        List<ShopDto.Res> shopDtos = this.shopService.readShopAll();
        Result result = new Result(shopDtos.size(),shopDtos);

        return ResponseEntity.ok(result);
    }

    @GetMapping("{shopId}")
    public ResponseEntity<ShopDto.Res> readShopOne(
            @PathVariable("shopId") Long shopId
    ) {
        return ResponseEntity.ok(this.shopService.readShopOne(shopId));
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("{shopId}")
    public ResponseEntity<?> updateShop(
            @PathVariable("shopId") Long shopId,
            @RequestBody ShopDto.Req shopDto) {

        shopService.updateShop(shopId, shopDto);
        return ResponseEntity.noContent().build();
    }

    @ResponseStatus(HttpStatus.ACCEPTED)
    @DeleteMapping("{shopId}")
    public ResponseEntity<?> deleteShop(
            @PathVariable("shopId") Long shopId) {
        this.shopService.deleteShop(shopId);
        return ResponseEntity.noContent().build();
    }

}
