package dev.yoon.challenge_community.controller;

import dev.yoon.challenge_community.model.CookieMsg;
import dev.yoon.challenge_community.repository.LogoutRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Iterator;

@Controller
@RequestMapping("/")
@RequiredArgsConstructor
public class HomeController {

    private final LogoutRepository logoutRepository;

    @GetMapping("home")
    public String home() {

        System.out.println("home coming");

        return "index";
    }

    @GetMapping
    public String root() {
        return "redirect:/home";
    }
}
