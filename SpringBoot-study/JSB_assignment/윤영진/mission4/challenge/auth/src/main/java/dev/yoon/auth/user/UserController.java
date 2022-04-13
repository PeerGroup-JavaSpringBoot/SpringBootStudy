package dev.yoon.auth.user;

import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class UserController {

    @GetMapping("/home")
    public String home() {
        return "index";
    }

    @GetMapping("/request-login")
    public String login(
            @RequestParam(value = "request_from", required = false) String request_from,
            HttpServletResponse response,
            HttpServletRequest request) throws IOException {
        System.out.println("login");
        System.out.println(request_from);

        if (request_from == null) {
            Cookie[] cookies = request.getCookies();
            for (int i = 0; i < cookies.length; i++) {
                if (cookies[i].getName().equals("likelion_login_cookie")) {
                    response.sendRedirect("http://localhost:9080/user/login?likelion_login_cookie=" + cookies[i].getValue());
                    break;
                }
            }
            return null;
        }
        return "user/loginForm";
    }

}
