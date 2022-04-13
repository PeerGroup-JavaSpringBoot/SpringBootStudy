package dev.aquashdw.community.controller;

import dev.aquashdw.community.auth.AuthenticationFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

// new
@Controller
@RequestMapping("/")
public class HomeController {
    private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
    private final AuthenticationFacade authFacade;

    public HomeController(
            @Autowired AuthenticationFacade authFacade
    ) {
        this.authFacade = authFacade;
    }

    @GetMapping
    public String root(){
        return "redirect:/home";
    }

    @GetMapping("home")
    public String home(){
//        try {
//            logger.info("connected user: {}",
//                    authFacade.getUserName());
//            logger.info(authFacade.getAuthentication().getClass().toString());
//        } catch (NullPointerException e) {
//            logger.info("no user logged in");
//        }
        return "index";
    }
}
