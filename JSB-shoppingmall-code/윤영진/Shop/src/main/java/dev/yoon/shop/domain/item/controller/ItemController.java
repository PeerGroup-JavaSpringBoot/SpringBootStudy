package dev.yoon.shop.domain.item.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ItemController {

    @GetMapping("admin/item/new")
    public String itemForm() {
        return "/item/itemForm";
    }
}
