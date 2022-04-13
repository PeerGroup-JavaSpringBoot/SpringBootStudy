package dev.aquashdw.community.service;

import dev.aquashdw.community.controller.dto.ShopDto;
import dev.aquashdw.community.controller.dto.ShopPostDto;
import dev.aquashdw.community.controller.dto.ShopReviewDto;
import dev.aquashdw.community.entity.*;
import dev.aquashdw.community.repository.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ShopService {
    private static final Logger logger = LoggerFactory.getLogger(ShopService.class);
    private final AreaRepository areaRepository;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;
    private final ShopRepository shopRepository;
    private final ShopPostRepository shopPostRepository;
    private final ShopReviewRepository shopReviewRepository;

    public ShopService(
            AreaRepository areaRepository,
            CategoryRepository categoryRepository,
            UserRepository userRepository,
            ShopRepository shopRepository,
            ShopPostRepository shopPostRepository,
            ShopReviewRepository shopReviewRepository
    ) {
        this.areaRepository = areaRepository;
        this.categoryRepository = categoryRepository;
        this.userRepository = userRepository;
        this.shopRepository = shopRepository;
        this.shopPostRepository = shopPostRepository;
        this.shopReviewRepository = shopReviewRepository;
    }

    public ShopDto createShop(ShopDto shopDto){
        Optional<UserEntity> userEntityOptional = this.userRepository.findById(shopDto.getOwnerId());
        if (userEntityOptional.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        UserEntity userEntity = userEntityOptional.get();
        if (!userEntity.getShopOwner()) throw new ResponseStatusException(HttpStatus.FORBIDDEN);

        Optional<CategoryEntity> categoryEntityOptional = this.categoryRepository.findById(shopDto.getCategory());
        if (categoryEntityOptional.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        CategoryEntity categoryEntity = categoryEntityOptional.get();

        Optional<AreaEntity> areaEntityOptional = this.areaRepository.findById(shopDto.getLocation());
        if (areaEntityOptional.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        AreaEntity location = areaEntityOptional.get();

        ShopEntity shopEntity = new ShopEntity();
        shopEntity.setName(shopDto.getName());
        shopEntity.setOwner(userEntity);
        shopEntity.setCategoryEntity(categoryEntity);
        shopEntity.setLocation(location);
        shopEntity = shopRepository.save(shopEntity);
        return new ShopDto(shopEntity);
    }

    public ShopDto readShop(Long id){
        Optional<ShopEntity> shopEntityOptional = this.shopRepository.findById(id);
        if (shopEntityOptional.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND);

        return new ShopDto(shopEntityOptional.get());
    }

    public List<ShopDto> readShopAll(){
        List<ShopDto> shopDtoList = new ArrayList<>();
        this.shopRepository.findAll().forEach(shopEntity -> shopDtoList.add(new ShopDto(shopEntity)));
        return shopDtoList;
    }

    public void updateShop(Long id, ShopDto shopDto){
        Optional<ShopEntity> shopEntityOptional = this.shopRepository.findById(id);
        if (shopEntityOptional.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        ShopEntity shopEntity = shopEntityOptional.get();
        if (!shopDto.getOwnerId().equals(shopEntity.getOwner().getId()))
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);

        shopEntity.setName(
                shopDto.getName() == null ? shopEntity.getName() : shopDto.getName()
        );

        this.shopRepository.save(shopEntity);
    }

    public void deleteShop(Long id){
        if (!this.shopRepository.existsById(id))
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        this.shopRepository.deleteById(id);
    }

    public ShopPostDto createShopPost(
            Long shopId,
            ShopPostDto shopPostDto){
        Optional<ShopEntity> shopEntityOptional = this.shopRepository.findById(shopId);
        if (shopEntityOptional.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        ShopEntity shopEntity = shopEntityOptional.get();

        if (!shopPostDto.getWriter().equals(shopEntity.getOwner().getId()))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);

        ShopPostEntity shopPostEntity = new ShopPostEntity();
        shopPostEntity.setTitle(shopPostDto.getTitle());
        shopPostEntity.setContent(shopPostDto.getContent());
        shopPostEntity.setShop(shopEntity);
        shopPostEntity = this.shopPostRepository.save(shopPostEntity);
        return new ShopPostDto(shopPostEntity);
    }

    public ShopPostDto readShopPost(Long shopId, Long postId){
        Optional<ShopPostEntity> shopPostEntityOptional = this.shopPostRepository.findById(postId);
        if (shopPostEntityOptional.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        ShopPostEntity shopPostEntity = shopPostEntityOptional.get();

        if (!shopPostEntity.getShop().getId().equals(shopId))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);

        return new ShopPostDto(shopPostEntity);
    }

    public List<ShopPostDto> readShopPostAll(Long shopId){
        List<ShopPostDto> shopPostDtoList = new ArrayList<>();
        this.shopPostRepository.findAll().forEach(
                shopPostEntity -> shopPostDtoList.add(new ShopPostDto(shopPostEntity)));
        return shopPostDtoList;
    }

    public void updateShopPost(Long shopId, Long postId, ShopPostDto shopPostDto){
        Optional<ShopPostEntity> shopPostEntityOptional = this.shopPostRepository.findById(postId);
        if (shopPostEntityOptional.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        ShopPostEntity shopPostEntity = shopPostEntityOptional.get();

        if (!shopPostEntity.getShop().getId().equals(shopId))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);

        shopPostEntity.setContent(
                shopPostDto.getContent() == null ? shopPostEntity.getContent() : shopPostDto.getContent()
        );
        shopPostEntity.setTitle(
                shopPostDto.getTitle() == null ? shopPostEntity.getTitle() : shopPostDto.getTitle()
        );
        this.shopPostRepository.save(shopPostEntity);
    }

    public void deleteShopPost(Long shopId, Long postId){
        Optional<ShopPostEntity> shopPostEntityOptional = this.shopPostRepository.findById(postId);
        if (shopPostEntityOptional.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        ShopPostEntity shopPostEntity = shopPostEntityOptional.get();

        if (!shopPostEntity.getShop().getId().equals(shopId))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);

        this.shopPostRepository.deleteById(postId);
    }

    public ShopReviewDto createShopReview(Long shopId, ShopReviewDto shopReviewDto){
        Optional<ShopEntity> shopEntityOptional = this.shopRepository.findById(shopId);
        if (shopEntityOptional.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        ShopEntity shopEntity = shopEntityOptional.get();

        Optional<UserEntity> writerOptional = this.userRepository.findById(shopReviewDto.getWriter());
        if (writerOptional.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        UserEntity writer = writerOptional.get();

        ShopReviewEntity shopReviewEntity = new ShopReviewEntity();
        shopReviewEntity.setTitle(shopReviewDto.getTitle());
        shopReviewEntity.setContent(shopReviewDto.getContent());
        shopReviewEntity.setShop(shopEntity);
        shopReviewEntity.setWriter(writer);
        shopReviewEntity.setGrade(shopReviewDto.getGrade());
        shopReviewEntity = this.shopReviewRepository.save(shopReviewEntity);
        return new ShopReviewDto(shopReviewEntity);
    }

    public ShopReviewDto readShopReview(Long shopId, Long reviewId) {
        Optional<ShopReviewEntity> shopReviewEntityOptional = this.shopReviewRepository.findById(reviewId);
        if (shopReviewEntityOptional.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        ShopReviewEntity shopReviewEntity = shopReviewEntityOptional.get();
        if (!shopReviewEntity.getShop().getId().equals(shopId))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);

        return new ShopReviewDto(shopReviewEntity);
    }

    public List<ShopReviewDto> readShopReviewAll(Long shopId){
        List<ShopReviewDto> shopReviewDtoList = new ArrayList<>();
        this.shopReviewRepository.findAll().forEach(
                shopReviewEntity -> shopReviewDtoList.add(new ShopReviewDto(shopReviewEntity)));
        return shopReviewDtoList;
    }

    public void updateShopReview(Long shopId, Long reviewId, ShopReviewDto shopReviewDto){
        Optional<ShopReviewEntity> shopReviewEntityOptional = this.shopReviewRepository.findById(reviewId);
        if (shopReviewEntityOptional.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        ShopReviewEntity shopReviewEntity = shopReviewEntityOptional.get();
        if (!shopReviewEntity.getShop().getId().equals(shopId))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);

        shopReviewEntity.setTitle(
                shopReviewDto.getTitle() == null ? shopReviewEntity.getTitle() : shopReviewDto.getTitle()
        );
        shopReviewEntity.setContent(
                shopReviewDto.getContent() == null ? shopReviewEntity.getContent() : shopReviewDto.getContent()
        );
        shopReviewEntity.setGrade(
                shopReviewDto.getGrade() == null ? shopReviewEntity.getGrade() : shopReviewDto.getGrade()
        );
        this.shopReviewRepository.save(shopReviewEntity);
    }

    public void deleteShopReview(Long shopId, Long reviewId) {
        Optional<ShopReviewEntity> shopReviewEntityOptional = this.shopReviewRepository.findById(reviewId);
        if (shopReviewEntityOptional.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        ShopReviewEntity shopReviewEntity = shopReviewEntityOptional.get();
        if (!shopReviewEntity.getShop().getId().equals(shopId))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);

        this.shopReviewRepository.deleteById(reviewId);

    }
}
