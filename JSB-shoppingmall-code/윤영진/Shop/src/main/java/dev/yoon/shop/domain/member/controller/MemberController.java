package dev.yoon.shop.domain.member.controller;

import dev.yoon.shop.domain.member.application.MemberService;
import dev.yoon.shop.domain.member.dto.MemberRegisterDto;
import dev.yoon.shop.domain.member.entity.Member;
import dev.yoon.shop.global.error.exception.BusinessException;
import dev.yoon.shop.global.error.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@RequestMapping("/members")
@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/new")
    public String memberForm(Model model) {
        model.addAttribute("memberFormDto", new MemberRegisterDto());
        return "member/memberForm";
    }

    @PostMapping("/new")
    private String memberForm(
            @Valid MemberRegisterDto memberRegisterDto,
            BindingResult bindingResult,
            Model model) {

        if (bindingResult.hasErrors()) {
            return "member/memberForm";
        }
        try {
            Member member = memberRegisterDto.toEntity();
            memberService.saveMember(member);
        }catch (BusinessException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "member/memberForm";
        }
        return "redirect:/";
    }

    @GetMapping("/login")
    public String loginMember() {

        return "/member/memberLoginForm";
    }

    @GetMapping("/login/error")
    public String loginError(Model model) {
        model.addAttribute("loginErrorMsg", ErrorCode.LOGIN_ERROR.getMessage());
        return "/member/memberLoginForm";
    }

}
