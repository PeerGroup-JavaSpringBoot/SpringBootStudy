package dev.yoon.shop.web.orderhist.controller;

import dev.yoon.shop.global.config.security.UserDetailsImpl;
import dev.yoon.shop.global.error.exception.BusinessException;
import dev.yoon.shop.web.orderhist.dto.OrderHistDto;
import dev.yoon.shop.web.orderhist.service.OrderHistService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping("/orderhist")
public class OrderHistController {

    private final int PAGE_SIZE = 6;
    private final int MAX_PAGE = 5;
    private final OrderHistService orderHistService;

    @GetMapping
    public String orderHist(
            Optional<Integer> page, Model model,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {

        Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, PAGE_SIZE);
        Page<OrderHistDto> pageOrderHistDtos = orderHistService.getOrderList(userDetails.getUsername(), pageable);

        model.addAttribute("orders", pageOrderHistDtos);
        model.addAttribute("page", pageable.getPageNumber());
        model.addAttribute("maxPage", MAX_PAGE);
        return "orderhist/orderhist";

    }

    @PostMapping("/{orderId}/cancel")
    public ResponseEntity<String> cancelOrder(@PathVariable("orderId") Long orderId, @AuthenticationPrincipal UserDetailsImpl userDetails) {

        try {
            orderHistService.cancelOrder(orderId, userDetails.getUsername());
        } catch (BusinessException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN);
        }
        return ResponseEntity.ok("order cancel success");

    }

}
