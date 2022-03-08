package dev.yoon.basic_board.controller;

import dev.yoon.basic_board.dto.Result;
import dev.yoon.basic_board.dto.shop.ShopPostDto;
import dev.yoon.basic_board.service.ShopPostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@Slf4j
@RequestMapping("shop/{shopId}/shop-post")
@RequiredArgsConstructor
public class ShopPostController {

    private final ShopPostService shopPostService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<ShopPostDto> creatShopPost(
            @PathVariable("shopId") Long shopId,
            @RequestBody ShopPostDto shopPostDto) {
        ShopPostDto dto = this.shopPostService.createShopPost(shopId, shopPostDto);

        if (dto == null) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(dto);
    }

    @GetMapping()
    public ResponseEntity<Result<List<ShopPostDto>>> readShopPostAll(
            @PathVariable("shopId") Long shopId
    ) {
        List<ShopPostDto> shopPostDtos = this.shopPostService.readShopPostAllbyShopId(shopId);

        if (shopPostDtos == null) {
            return ResponseEntity.notFound().build();
        }
        Result result = new Result(shopPostDtos.size(), shopPostDtos);

        return ResponseEntity.ok(result);
    }

    @GetMapping("{shopPostId}")
    public ResponseEntity<ShopPostDto> readShopPostOne(
            @PathVariable("shopId") Long shopId,
            @PathVariable("shopPostId") Long shopPostId) {

        ShopPostDto shopPostDto = this.shopPostService.readShopPostOneByShopId(shopId, shopPostId);
        if (shopPostDto == null)
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok(shopPostDto);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("{shopPostId}")
    public ResponseEntity<?> updateShopPost(
            @PathVariable("shopId") Long shopId,
            @PathVariable("shopPostId") Long shopPostId,
            @RequestBody ShopPostDto shopPostDto) {

        if (!shopPostService.updateShopPost(shopId, shopPostId, shopPostDto))
            return ResponseEntity.notFound().build();
        return ResponseEntity.noContent().build();
    }

    @ResponseStatus(HttpStatus.ACCEPTED)
    @DeleteMapping("{shopPostId}")
    public ResponseEntity<?> deleteShopPost(
            @PathVariable("shopId") Long shopId,
            @PathVariable("shopPostId") Long shopPostId) {

        if (!this.shopPostService.deleteShopPost(shopId, shopPostId))
            return ResponseEntity.notFound().build();

        return ResponseEntity.noContent().build();

    }


}
