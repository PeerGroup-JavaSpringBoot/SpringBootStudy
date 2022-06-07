package dev.yoon.shop.web.cart.controller;

import dev.yoon.shop.global.config.security.UserDetailsImpl;
import dev.yoon.shop.web.cart.dto.CartDetailDto;
import dev.yoon.shop.web.cart.service.CartHistService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/cart")
@RequiredArgsConstructor
public class CartHistCotroller {

    private final CartHistService cartHistService;

    @GetMapping
    public String orderHist(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            Model model) {
        List<CartDetailDto> cartDetailList = cartHistService.getCartList(userDetails.getUsername());
        model.addAttribute("cartItems", cartDetailList);
        return "cart/cartList";
    }

    @PatchMapping("/cartItem/{cartItemId}")
    public ResponseEntity updateCartItem(
            @RequestParam("count") int count,
            @PathVariable("cartItemId") Long cartItemId,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {

        if (count <= 0) {
            return new ResponseEntity<String>("최소 1개 이상 담아주세요.", HttpStatus.BAD_REQUEST);
        }

        cartHistService.updateCartItemCount(cartItemId, count);
        return new ResponseEntity<Long>(cartItemId, HttpStatus.OK);
    }

    @DeleteMapping(value = "/cartItem/{cartItemId}")
    public @ResponseBody ResponseEntity deleteCartItem(@PathVariable("cartItemId") Long cartItemId, Principal principal){

//        if(!cartService.validateCartItem(cartItemId, principal.getName())){
//            return new ResponseEntity<String>("수정 권한이 없습니다.", HttpStatus.FORBIDDEN);
//        }

        cartHistService.deleteCartItem(cartItemId);

        return new ResponseEntity<Long>(cartItemId, HttpStatus.OK);
    }

}
