package dev.yoon.challenge_community.controller;

import dev.yoon.challenge_community.dto.common.Result;
import dev.yoon.challenge_community.dto.shop.ShopPostDto;
import dev.yoon.challenge_community.service.ShopPostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("shop/{shopId}/shop-post")
@RequiredArgsConstructor
public class ShopPostController {

    private final ShopPostService shopPostService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<ShopPostDto.Res> creatShopPost(
            @PathVariable("shopId") Long shopId,
            @RequestBody @Valid ShopPostDto.Req shopPostDto) {

        return ResponseEntity.ok(this.shopPostService.createShopPost(shopId, shopPostDto));
    }

    @GetMapping()
    public ResponseEntity<Result<List<ShopPostDto.Res>>> readShopPostAll(
            @PathVariable("shopId") Long shopId
    ) {
        List<ShopPostDto.Res> shopPostDtos = this.shopPostService.readShopPostAllbyShopId(shopId);

        Result result = new Result(shopPostDtos.size(), shopPostDtos);

        return ResponseEntity.ok(result);
    }

}
