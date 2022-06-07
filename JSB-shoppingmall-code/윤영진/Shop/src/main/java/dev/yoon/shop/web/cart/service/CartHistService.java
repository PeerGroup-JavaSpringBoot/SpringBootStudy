package dev.yoon.shop.web.cart.service;

import dev.yoon.shop.domain.cart.entity.Cart;
import dev.yoon.shop.domain.cart.service.CartService;
import dev.yoon.shop.domain.cartitem.entity.CartItem;
import dev.yoon.shop.domain.cartitem.service.CartItemService;
import dev.yoon.shop.domain.member.application.MemberService;
import dev.yoon.shop.domain.member.entity.Member;
import dev.yoon.shop.web.cart.dto.CartDetailDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CartHistService {

    private final MemberService memberService;
    private final CartService cartService;
    private final CartItemService cartItemService;

    @Transactional(readOnly = true)
    public List<CartDetailDto> getCartList(String email){

        List<CartDetailDto> cartDetailDtoList = new ArrayList<>();

        Member member = memberService.getMemberByEmail(email);

        Cart cart = cartService.getCartByMemberId(member.getId());

        if(cart == null){
            return cartDetailDtoList;
        }

        cartDetailDtoList = cartItemService.getCartDetailDtoList(cart.getId());
        return cartDetailDtoList;
    }

    @Transactional
    public void updateCartItemCount(Long cartItemId, int count) {

        CartItem cartItem = cartItemService.getCartItemById(cartItemId);
        cartItem.updateCount(count);

    }

    @Transactional
    public void deleteCartItem(Long cartItemId) {

        cartItemService.deleteCartItem(cartItemId);

    }
}
