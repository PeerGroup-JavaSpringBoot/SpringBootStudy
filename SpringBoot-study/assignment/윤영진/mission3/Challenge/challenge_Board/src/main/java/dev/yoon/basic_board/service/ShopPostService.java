package dev.yoon.basic_board.service;

import dev.yoon.basic_board.domain.Shop;
import dev.yoon.basic_board.domain.ShopPost;
import dev.yoon.basic_board.domain.User;
import dev.yoon.basic_board.dto.shop.ShopPostDto;
import dev.yoon.basic_board.repository.ShopPostRepository;
import dev.yoon.basic_board.repository.ShopRepository;
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
public class ShopPostService {

    private final ShopRepository shopRepository;
    private final UserRepository userRepository;
    private final ShopPostRepository shopPostRepository;

    public ShopPostDto createShopPost(Long shopId, ShopPostDto shopPostDto) {

        Optional<User> optionalUser = userRepository.findById(shopPostDto.getUserId());
        Optional<Shop> optionalShop = shopRepository.findById(shopId);

        if (optionalUser.isEmpty() || optionalShop.isEmpty())
            return null;

        shopPostDto.setShopId(shopId);

        User user = optionalUser.get();
        Shop shop = optionalShop.get();

        // 본인 가게에 대한 예외처리
        if (shop.getUser() != user)
            return null;

        ShopPost shopPost = new ShopPost(shopPostDto);
        shop.addShopPost(shopPost);
        user.addShopPost(shopPost);

        shopPostRepository.save(shopPost);

        return shopPostDto;
    }


    public List<ShopPostDto> readShopPostAllbyShopId(Long shopId) {
        List<ShopPost> shopPostList = this.shopPostRepository.findShopPostsByShop(shopId);

        if (shopPostList.isEmpty())
            return null;

        List<ShopPostDto> shopPostDtos = new ArrayList<>();
        for (ShopPost shopPost : shopPostList) {
            ShopPostDto shopPostDto = ShopPost.createShopPostDto(shopPost);
            shopPostDtos.add(shopPostDto);
        }
        return shopPostDtos;

    }

    public ShopPostDto readShopPostOneByShopId(Long shopId, Long shopPostId) {

        ShopPost shopPost = this.shopPostRepository.findShopPostByShop(shopId, shopPostId);

        if (shopPost == null) {
            return null;
        }
        ShopPostDto shopPostDto = ShopPost.createShopPostDto(shopPost);
        return shopPostDto;
    }

    public boolean updateShopPost(Long shopId, Long shopPostId, ShopPostDto shopPostDto) {
        Optional<Shop> optionalShop = shopRepository.findById(shopId);
        Optional<ShopPost> optionalShopPost = shopPostRepository.findById(shopPostId);

        if (optionalShop.isEmpty() || optionalShopPost.isEmpty())
            return false;
        Shop shop = optionalShop.get();
        ShopPost shopPost = optionalShopPost.get();

        // 해당 shop에 shoppost가 안들어있는 경우 예외
        if (!shop.getShopPosts().contains(shopPost))
            return false;

        shopPost.update(shopPostDto);
        return true;
    }

    public boolean deleteShopPost(Long shopId, Long shopPostId) {

        Optional<Shop> optionalShop = shopRepository.findById(shopId);
        Optional<ShopPost> optionalShopPost = shopPostRepository.findById(shopPostId);

        if (optionalShop.isEmpty() || optionalShopPost.isEmpty()){
            System.out.println("check2");
            return false;
        }

        Shop shop = optionalShop.get();
        ShopPost shopPost = optionalShopPost.get();

        // 해당 shop에 shoppost가 안들어있는 경우 예외
        if (!shop.getShopPosts().contains(shopPost)) {
            System.out.println("cehck1");
            return false;

        }

        this.shopPostRepository.delete(shopPost);
        return true;
    }
}
