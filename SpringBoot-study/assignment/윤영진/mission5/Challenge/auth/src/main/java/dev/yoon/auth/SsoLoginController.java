package dev.yoon.auth;

import dev.yoon.auth.model.CookieProcess;
import dev.yoon.auth.service.RedisService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Controller
@RequiredArgsConstructor
@Slf4j
public class SsoLoginController {

    private final RedisService redisService;

    @GetMapping("request-login")
    public String requestLogin(
            HttpServletRequest request,
            @RequestParam("request_from") String requestFrom
    ) {
        log.info("request_login");
        String cookieValue = "";

        for (Cookie cookie : request.getCookies()) {

            if (cookie.getName().equals("likelion_login_cookie")) {
                cookieValue = cookie.getValue();
                break;
            }
        }

        return String.format(
                "redirect:%s?likelion_login_cookie=%s", requestFrom, cookieValue);
    }

    @GetMapping("current-user")
    public @ResponseBody
    CookieProcess getLoginUser(
            @RequestParam("cookie-value") String cookie_value
    ) {
        log.info("cookie-value: {}",cookie_value);
        return redisService.retrieveJob(cookie_value);
    }

}
