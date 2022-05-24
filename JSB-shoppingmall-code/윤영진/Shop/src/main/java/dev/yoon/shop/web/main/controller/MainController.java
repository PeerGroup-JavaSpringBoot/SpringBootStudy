package dev.yoon.shop.web.main.controller;

import dev.yoon.shop.global.config.security.UserDetailsImpl;
import dev.yoon.shop.web.main.dto.MainItemDto;
import dev.yoon.shop.web.main.service.MainService;
import dev.yoon.shop.web.manageitem.dto.ItemSearchDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Optional;

import static dev.yoon.shop.global.constant.BaseConst.SET_PAGE_ITEM_MAX_COUNT;
import static dev.yoon.shop.global.constant.BaseConst.SET_PAGE_MAX_COUNT;


@Controller
@RequiredArgsConstructor
public class MainController {

    private final MainService mainService;

    @GetMapping("/")
    public String main(ItemSearchDto itemSearchDto, Optional<Integer> page, Model model) {


        Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, SET_PAGE_ITEM_MAX_COUNT);
        Page<MainItemDto> mainItemDtos = mainService.getMainItemPage(itemSearchDto, pageable);

        model.addAttribute("items", mainItemDtos);
        model.addAttribute("itemSearchDto", itemSearchDto);
        model.addAttribute("maxPage", SET_PAGE_MAX_COUNT);


        return "main";
    }

}
