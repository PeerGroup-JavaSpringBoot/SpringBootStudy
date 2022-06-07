package dev.yoon.shop.web.cart.controller;

import dev.yoon.shop.global.config.security.UserDetailsImpl;
import dev.yoon.shop.web.cart.dto.CartOrderDto;
import dev.yoon.shop.web.cart.service.CartOrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("/cart")
@RequiredArgsConstructor
public class CartOrderController {

    private final CartOrderService cartOrderService;

    @PostMapping("/orders")
    public ResponseEntity orderCartItem(@RequestBody CartOrderDto cartOrderDto,@AuthenticationPrincipal UserDetailsImpl userDetails){

        List<CartOrderDto> cartOrderDtoList = cartOrderDto.getCartOrderDtoList();

        if(cartOrderDtoList == null || cartOrderDtoList.size() == 0){
            return new ResponseEntity<String>("주문할 상품을 선택해주세요", HttpStatus.FORBIDDEN);
        }

//        for (CartOrderDto cartOrder : cartOrderDtoList) {
//            if(!cartService.validateCartItem(cartOrder.getCartItemId(), principal.getName())){
//                return new ResponseEntity<String>("주문 권한이 없습니다.", HttpStatus.FORBIDDEN);
//            }
//        }

        Long orderId = cartOrderService.orderCartItem(cartOrderDtoList, userDetails.getUsername());
        return new ResponseEntity<Long>(orderId, HttpStatus.OK);
    }
}
