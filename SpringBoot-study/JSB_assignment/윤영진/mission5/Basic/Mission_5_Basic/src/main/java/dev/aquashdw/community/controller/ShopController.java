package dev.aquashdw.community.controller;

import dev.aquashdw.community.controller.dto.ShopDto;
import dev.aquashdw.community.controller.dto.ShopPostDto;
import dev.aquashdw.community.controller.dto.ShopReviewDto;
import dev.aquashdw.community.entity.ShopEntity;
import dev.aquashdw.community.service.ShopService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("shop")
public class ShopController {
    private static final Logger logger = LoggerFactory.getLogger(ShopController.class);
    private final ShopService shopService;

    public ShopController(ShopService shopService) {
        this.shopService = shopService;
    }

    @PostMapping
    public ResponseEntity<ShopDto> createShop(@RequestBody ShopDto shopDto){
        return ResponseEntity.ok(this.shopService.createShop(shopDto));
    }

    @GetMapping("{id}")
    public ResponseEntity<ShopDto> readShop(@PathVariable("id") Long id) {
        return ResponseEntity.ok(this.shopService.readShop(id));
    }

    @GetMapping
    public ResponseEntity<Collection<ShopDto>> readShopAll(){
        return ResponseEntity.ok(this.shopService.readShopAll());
    }

    @PutMapping("{id}")
    public ResponseEntity<?> updateShop(@PathVariable("id") Long id, @RequestBody ShopDto shopDto){
        this.shopService.updateShop(id, shopDto);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteShop(@PathVariable("id") Long id) {
        this.shopService.deleteShop(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("{shopId}/post")
    public ResponseEntity<ShopPostDto> createShopPost(
            @PathVariable("shopId") Long shopId,
            @RequestBody ShopPostDto shopPostDto){
        return ResponseEntity.ok(this.shopService.createShopPost(shopId, shopPostDto));
    }

    @GetMapping("{shopId}/post/{postId}")
    public ResponseEntity<ShopPostDto> readShopPost(
            @PathVariable("shopId") Long shopId, @PathVariable("postId") Long postId
    ) {
        return ResponseEntity.ok(this.shopService.readShopPost(shopId, postId));
    }

    @GetMapping("{shopId}/post")
    public ResponseEntity<Collection<ShopPostDto>> readShopPostAll(
            @PathVariable("shopId") Long shopId
    ) {
        return ResponseEntity.ok(this.shopService.readShopPostAll(shopId));
    }

    @PutMapping("{shopId}/post/{postId}")
    public ResponseEntity<?> updateShopPost(
            @PathVariable("shopId") Long shopId,
            @PathVariable("postId") Long postId,
            @RequestBody ShopPostDto shopPostDto
    ){
        this.shopService.updateShopPost(shopId, postId, shopPostDto);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("{shopId}/post/{postId}")
    public ResponseEntity<?> deleteShopPost(
            @PathVariable("shopId") Long shopId,
            @PathVariable("postId") Long postId
    ){
        this.shopService.deleteShopPost(shopId, postId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("{shopId}/review")
    public ResponseEntity<ShopReviewDto> createShopReview(
            @PathVariable("shopId") Long shopId,
            @RequestBody ShopReviewDto shopReviewDto){
        return ResponseEntity.ok(this.shopService.createShopReview(shopId, shopReviewDto));
    }

    @GetMapping("{shopId}/review/{reviewId}")
    public ResponseEntity<ShopReviewDto> readShopReview(
            @PathVariable("shopId") Long shopId,
            @PathVariable("reviewId") Long reviewId
    ) {
        return ResponseEntity.ok(this.shopService.readShopReview(shopId, reviewId));
    }

    @GetMapping("{shopId}/review")
    public ResponseEntity<Collection<ShopReviewDto>> readShopReviewAll(
            @PathVariable("shopId") Long shopId
    ) {
        return ResponseEntity.ok(this.shopService.readShopReviewAll(shopId));
    }

    @PutMapping("{shopId}/review/{reviewId}")
    public ResponseEntity<?> updateShopReview(
            @PathVariable("shopId") Long shopId,
            @PathVariable("reviewId") Long reviewId,
            @RequestBody ShopReviewDto shopReviewDto
    ){
        this.shopService.updateShopReview(shopId, reviewId, shopReviewDto);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("{shopId}/review/{reviewId}")
    public ResponseEntity<?> deleteShopReview(
            @PathVariable("shopId") Long shopId,
            @PathVariable("reviewId") Long reviewId
    ){
        this.shopService.deleteShopReview(shopId, reviewId);
        return ResponseEntity.noContent().build();
    }
}
