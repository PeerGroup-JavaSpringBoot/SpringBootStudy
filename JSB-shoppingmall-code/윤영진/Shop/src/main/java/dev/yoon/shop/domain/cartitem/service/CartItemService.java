package dev.yoon.shop.domain.cartitem.service;

import dev.yoon.shop.domain.cart.entity.Cart;
import dev.yoon.shop.domain.cartitem.entity.CartItem;
import dev.yoon.shop.domain.cartitem.repository.CartItemRepository;
import dev.yoon.shop.web.cart.dto.CartDetailDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CartItemService {

    private final CartItemRepository cartItemRepository;

    public CartItem getCartItemById(Long cartItemId) {
        return cartItemRepository.findById(cartItemId)
                .orElseThrow(EntityNotFoundException::new);
    }


    public CartItem getCartItemByCartIdAndItemId(Long cartId, Long ItemId) {

        Optional<CartItem> optionalCartItem = cartItemRepository.findByCartIdAndItemId(cartId, ItemId);

        if (optionalCartItem.isEmpty()) {
            return null;
        }

        return optionalCartItem.get();

    }

    @Transactional
    public void saveCartItem(CartItem cartItem) {

        cartItemRepository.save(cartItem);
    }


    public List<CartDetailDto> getCartDetailDtoList(Long id) {

        return cartItemRepository.findCartDetailDtoList(id);

    }


    public void deleteCartItem(Long cartItemId) {

        CartItem cartItem = getCartItemById(cartItemId);
        cartItemRepository.delete(cartItem);

    }
}
