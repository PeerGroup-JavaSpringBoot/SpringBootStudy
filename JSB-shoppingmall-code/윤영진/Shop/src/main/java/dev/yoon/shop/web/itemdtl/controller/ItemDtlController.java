package dev.yoon.shop.web.itemdtl.controller;

import dev.yoon.shop.global.config.security.UserDetailsImpl;
import dev.yoon.shop.web.itemdtl.dto.CartItemDto;
import dev.yoon.shop.web.itemdtl.dto.ItemDtlDto;
import dev.yoon.shop.web.itemdtl.dto.OrderDto;
import dev.yoon.shop.web.itemdtl.service.ItemDtlService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/itemdtl")
public class ItemDtlController {

    private final ItemDtlService itemDtlService;

    @GetMapping(value = "/{itemId}")
    public String itemDtl(Model model, @PathVariable("itemId") Long itemId) {

        ItemDtlDto itemDtlDto = itemDtlService.getItemDtl(itemId);
        model.addAttribute("item", itemDtlDto);
        return "itemdtl/itemdtl";

    }

    @PostMapping("/order")
    public ResponseEntity order(
            @RequestBody @Valid OrderDto orderDto,
            BindingResult bindingResult,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {

        if (bindingResult.hasErrors()) {

            StringBuilder sb = new StringBuilder();
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            for (FieldError fieldError : fieldErrors) {
                sb.append(fieldError.getDefaultMessage());
            }
            return new ResponseEntity(sb.toString(), HttpStatus.BAD_REQUEST);

        }


        try {
            String email = userDetails.getUsername();
            itemDtlService.order(orderDto, email);

        }catch (NullPointerException e) {
            return new ResponseEntity("login plz", HttpStatus.UNAUTHORIZED);
        }
        catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

        return ResponseEntity.ok("order success");
    }

    @PostMapping("/cart")
    public ResponseEntity cartOrder(
            @Valid @RequestBody CartItemDto cartItemDto,
            BindingResult bindingResult,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {

        if (bindingResult.hasErrors()) {
            StringBuilder sb = new StringBuilder();
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            for (FieldError fieldError : fieldErrors) {
                sb.append(fieldError.getDefaultMessage());
            }
            return new ResponseEntity<String>(sb.toString(), HttpStatus.BAD_REQUEST);
        }
        String email = userDetails.getUsername();
        Long cartItemId;
        try {
            cartItemId = itemDtlService.cartOrder(cartItemDto, email);
        }catch (Exception e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

        return ResponseEntity.ok(cartItemId);
    }



}
