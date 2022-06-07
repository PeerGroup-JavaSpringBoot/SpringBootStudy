package dev.yoon.shop.domain.cart.service;

import dev.yoon.shop.domain.cart.entity.Cart;
import dev.yoon.shop.domain.cart.repository.CartRepository;
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
public class CartService {

    private final CartRepository cartRepository;

    public Cart getCartByMemberId(Long memberId) {

        Optional<Cart> optionalCart = cartRepository.findByMemberId(memberId);

        if (optionalCart.isEmpty()) {
            return null;
        }
        return optionalCart.get();

    }

    public void saveCart(Cart cart) {
        cartRepository.save(cart);
    }



}
