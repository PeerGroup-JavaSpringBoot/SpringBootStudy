package dev.yoon.basic_board.service;

import dev.yoon.basic_board.domain.Shop;
import dev.yoon.basic_board.domain.ShopReview;
import dev.yoon.basic_board.domain.User;
import dev.yoon.basic_board.dto.shop.ShopReviewDto;
import dev.yoon.basic_board.repository.ShopRepository;
import dev.yoon.basic_board.repository.ShopReviewRepository;
import dev.yoon.basic_board.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class ShopReviewService {

    private final ShopRepository shopRepository;
    private final UserRepository userRepository;
    private final ShopReviewRepository shopReviewRepository;

    public ShopReviewDto createShopReview(Long shopId, ShopReviewDto shopReviewDto) {

        Optional<User> optionalUser = userRepository.findById(shopReviewDto.getUserId());
        Optional<Shop> optionalShop = shopRepository.findById(shopId);

        if(optionalUser.isEmpty() || optionalShop.isEmpty())
            return null;

        shopReviewDto.setShopId(shopId);

        User user = optionalUser.get();
        Shop shop = optionalShop.get();

        ShopReview shopReview = new ShopReview(shopReviewDto);
        shop.addShopReview(shopReview);
        user.addShopReview(shopReview);

        shopReviewRepository.save(shopReview);

        return shopReviewDto;
    }

    public List<ShopReviewDto> readShopReviewAllbyShopId(Long shopId) {
        List<ShopReview> shopReviewList = this.shopReviewRepository.findShopReviewsByShop(shopId);

        if (shopReviewList.isEmpty())
            return null;

        List<ShopReviewDto> shopReviewDtos = new ArrayList<>();
        for (ShopReview shopReview : shopReviewList) {
            ShopReviewDto shopReviewDto = ShopReview.createShopReviewDto(shopReview);
            shopReviewDtos.add(shopReviewDto);
        }
        return shopReviewDtos;


    }

    public ShopReviewDto readShopReviewOneByShopId(Long shopId, Long shopReviewId) {
        ShopReview shopReview = this.shopReviewRepository.findShopReviewByShop(shopId, shopReviewId);

        if (shopReview == null) {
            return null;
        }
        ShopReviewDto shopReviewDto = ShopReview.createShopReviewDto(shopReview);
        return shopReviewDto;


    }

    public boolean updateShopReview(Long shopId, Long shopReviewId, ShopReviewDto shopReviewDto) {

        Optional<Shop> optionalShop = shopRepository.findById(shopId);
        Optional<ShopReview> optionalShopReview = shopReviewRepository.findById(shopReviewId);

        if (optionalShop.isEmpty() || optionalShopReview.isEmpty())
            return false;

        Shop shop = optionalShop.get();
        ShopReview shopReview = optionalShopReview.get();

        // 해당 shop에 shoppost가 안들어있는 경우 예외
        if (!shop.getShopReviews().contains(shopReview))
            return false;

        shopReview.update(shopReviewDto);
        return true;


    }

    public boolean deleteShopReview(Long shopId, Long shopReviewId) {

        Optional<Shop> optionalShop = shopRepository.findById(shopId);
        Optional<ShopReview> optionalShopReview = shopReviewRepository.findById(shopReviewId);

        if (optionalShop.isEmpty() || optionalShopReview.isEmpty())
            return false;

        Shop shop = optionalShop.get();
        ShopReview shopReview = optionalShopReview.get();

        // 해당 shop에 shoppost가 안들어있는 경우 예외
        if (!shop.getShopReviews().contains(shopReview))
            return false;

        shopReviewRepository.deleteById(shopReviewId);
        return true;

    }
}
