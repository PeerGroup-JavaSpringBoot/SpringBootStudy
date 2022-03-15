package dev.yoon.basic_board.controller;

import dev.yoon.basic_board.dto.Result;
import dev.yoon.basic_board.dto.shop.ShopReviewDto;
import dev.yoon.basic_board.service.ShopReviewService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("shop/{shopId}/shop-review")
@RequiredArgsConstructor
public class ShopReviewController {

    private final ShopReviewService shopReviewSerive;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<ShopReviewDto> creatShopReview(
            @PathVariable("shopId") Long shopId,
            @RequestBody ShopReviewDto shopReviewDto) {
        ShopReviewDto dto = this.shopReviewSerive.createShopReview(shopId,shopReviewDto);

        if(dto == null) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(dto);
    }

    @GetMapping()
    public ResponseEntity<Result<List<ShopReviewDto>>> readShopReviewAll(
            @PathVariable("shopId") Long shopId
    ) {
        List<ShopReviewDto> shopReviewDtos = this.shopReviewSerive.readShopReviewAllbyShopId(shopId);

        if (shopReviewDtos == null) {
            return ResponseEntity.notFound().build();
        }
        Result result = new Result(shopReviewDtos.size(),shopReviewDtos);

        return ResponseEntity.ok(result);
    }

    @GetMapping("{shopReviewId}")
    public ResponseEntity<ShopReviewDto> readShopReviewOne(
            @PathVariable("shopId") Long shopId,
            @PathVariable("shopReviewId") Long shopReviewId) {

        ShopReviewDto shopReviewDtos = this.shopReviewSerive.readShopReviewOneByShopId(shopId, shopReviewId);
        if (shopReviewDtos == null)
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok(shopReviewDtos);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("{shopReviewId}")
    public ResponseEntity<?> updateShopReview(
            @PathVariable("shopId") Long shopId,
            @PathVariable("shopReviewId") Long shopReviewId,
            @RequestBody ShopReviewDto shopReviewDto) {

        if (!shopReviewSerive.updateShopReview(shopId, shopReviewId, shopReviewDto))
            return ResponseEntity.notFound().build();
        return ResponseEntity.noContent().build();
    }

    @ResponseStatus(HttpStatus.ACCEPTED)
    @DeleteMapping("{shopReviewId}")
    public ResponseEntity<?> deleteShopReview(
            @PathVariable("shopId") Long shopId,
            @PathVariable("shopReviewId") Long shopReviewId) {

        if (!this.shopReviewSerive.deleteShopReview(shopId, shopReviewId))
            return ResponseEntity.notFound().build();

        return ResponseEntity.noContent().build();

    }


}
