package dev.yoon.basic_community.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("home")
@RequiredArgsConstructor
@Slf4j
public class HomeController {

//    private final AuthenticationFacade authenticationFacade;

    @GetMapping
    public String home(Authentication authentication) {
        try {
//            Object details = authenticationFacade.getAuthentication().getDetails();
//            log.info("detatils: {}", details);

//            Object details = SecurityContextHolder.getContext().getAuthentication().getDetails();
//            log.info("detatils: {}",details);
//
//            log.info("connected user:{}", authentication.getName());
//            log.info("get Details:{}", authentication.getDetails());
//            log.info("connected user:{}", principal.getName());
        } catch (NullPointerException e) {
            log.info("no user logged in");
        }
        return "index";
    }
}
