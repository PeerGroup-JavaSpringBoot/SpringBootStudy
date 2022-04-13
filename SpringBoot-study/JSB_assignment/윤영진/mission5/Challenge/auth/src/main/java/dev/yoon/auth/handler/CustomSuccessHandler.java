package dev.yoon.auth.handler;

import dev.yoon.auth.model.CookieProcess;
import dev.yoon.auth.repository.RedisRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class CustomSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
    private static final int COOKIE_EXPIRY = 30 * 24 * 60 * 60;
    private final RedisRepository redisRepository;

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication
    ) throws IOException, ServletException {


        System.out.println("CustomSuccessHandler !!!");
        String cookieValue = UUID.randomUUID().toString();
        /**
         * 적재
         */
        CookieProcess cookieProcess = new CookieProcess();
        cookieProcess.setId(cookieValue);

        cookieProcess.setName(authentication.getName());
        redisRepository.save(cookieProcess);

        Cookie cookie = new Cookie(
                "likelion_login_cookie",
                cookieValue);
        cookie.setMaxAge(COOKIE_EXPIRY);
        cookie.setPath("/");
        response.addCookie(cookie);

        /**
         * 기본 redircet url이 정의되어있는 조상 객체 사용
         * 기존에 로그인이 안되서 가지못한 request-login에 접근 가능
         */
        super.onAuthenticationSuccess(request, response, authentication);
    }
}
