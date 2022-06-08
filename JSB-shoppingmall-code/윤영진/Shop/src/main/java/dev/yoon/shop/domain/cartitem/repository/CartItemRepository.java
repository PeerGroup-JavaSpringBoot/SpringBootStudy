package dev.yoon.shop.domain.cartitem.repository;

import dev.yoon.shop.domain.cartitem.entity.CartItem;
import dev.yoon.shop.web.cart.dto.CartDetailDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    @Query("select new dev.yoon.shop.web.cart.dto.CartDetailDto(ci.id, i.itemNm, i.price, ci.count, im.imgUrl) " +
            "from CartItem ci, ItemImage im " +
            "join ci.item i " +
            "where ci.cart.id = :cartId " +
            "and im.item.id = ci.item.id " +
            "and im.isRepImg = TRUE " +
            "order by ci.regTime desc"
    )
    List<CartDetailDto> findCartDetailDtoList(Long cartId);


    Optional<CartItem> findByCartIdAndItemId(Long cartId, Long itemId);
}
