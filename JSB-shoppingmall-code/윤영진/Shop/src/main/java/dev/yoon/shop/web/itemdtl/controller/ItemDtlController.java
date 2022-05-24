package dev.yoon.shop.web.itemdtl.controller;

import dev.yoon.shop.web.itemdtl.dto.ItemDtlDto;
import dev.yoon.shop.web.itemdtl.service.ItemDtlService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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



}
