package dev.yoon.shop.web.adminitem.controller;

import dev.yoon.shop.domain.item.entity.Item;
import dev.yoon.shop.global.config.security.UserDetailsImpl;
import dev.yoon.shop.global.error.exception.EntityNotFoundException;
import dev.yoon.shop.global.error.exception.ErrorCode;
import dev.yoon.shop.web.adminitem.dto.ItemFormDto;
import dev.yoon.shop.web.adminitem.dto.UpdateItemDto;
import dev.yoon.shop.web.adminitem.service.AdminItemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/admin/item")
@RequiredArgsConstructor
public class AdminItemController {

    private final AdminItemService adminItemService;

    @GetMapping("/new")
    public String itemForm(Model model) {
        model.addAttribute("itemFormDto", new ItemFormDto());
        return "adminitem/registeritemform";
    }

    @PostMapping("/new")
    private String itemNew(
            @Valid @ModelAttribute("itemFormDto") ItemFormDto dto,
            BindingResult bindingResult,
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            RedirectAttributes redirectAttributes
    ) {

        if (dto.getItemImageFiles().get(0).isEmpty()) {
            bindingResult.reject("requiredFirstItemImage", ErrorCode.REQUIRED_REPRESENT_IMAGE.getMessage());
            return "adminitem/registeritemform";
        }
        if (bindingResult.hasErrors()) {
            return "adminitem/registeritemform";
        }

        String email = userDetails.getUsername();
        try {
            adminItemService.saveItem(dto, email);
        } catch (Exception e) {
            log.error(e.getMessage());
            bindingResult.reject("globalError", "상품 등록 중 에러가 발생하였습니다.");
            return "adminitem/registeritemform";
        }

        return "redirect:/";
    }

    @GetMapping("/{itemId}")
    public String itemEdit(@PathVariable Long itemId, Model model) {
        try {
            UpdateItemDto updateItemDto = adminItemService.getUpdateItemDto(itemId);
            model.addAttribute("updateItemDto", updateItemDto);
        }catch (EntityNotFoundException e){
            model.addAttribute("errorMessage", "존재하지 않는 상품 입니다.");
            model.addAttribute("updateItemDto", new UpdateItemDto());
            return "adminitem/updateitemform";
        }

        return "adminitem/updateitemform";
    }

    @PostMapping("/{itemId}")
    public String itemEdit(
            @PathVariable Long itemId,
            @Valid UpdateItemDto updateItemDto,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes
    ) {
        if (updateItemDto.getItemImageFiles().get(0).isEmpty() &&
                !StringUtils.hasText(updateItemDto.getOriginalImageNames().get(0))) {
            bindingResult.reject("requiredInputFirstImage", ErrorCode.REQUIRED_REPRESENT_IMAGE.getMessage());
            List<UpdateItemDto.ItemImageDto> itemImageDtos = adminItemService.getItemImageDtos(itemId);
            updateItemDto.setItemImageDtos(itemImageDtos);
            return "adminitem/updateitemform";
        } else if (bindingResult.hasErrors()) {
            List<UpdateItemDto.ItemImageDto> itemImageDtos = adminItemService.getItemImageDtos(itemId);
            updateItemDto.setItemImageDtos(itemImageDtos);
            return "adminitem/updateitemform";
        }

        try {
            adminItemService.updateItem(updateItemDto);
        } catch (Exception e) {
            log.error(e.getMessage());
            bindingResult.reject("globalError", "상품 등록 중 에러가 발생하였습니다.");
            return "adminitem/updateitemform";
        }

        redirectAttributes.addAttribute("itemId", itemId);
        return "redirect:/";

    }



}
