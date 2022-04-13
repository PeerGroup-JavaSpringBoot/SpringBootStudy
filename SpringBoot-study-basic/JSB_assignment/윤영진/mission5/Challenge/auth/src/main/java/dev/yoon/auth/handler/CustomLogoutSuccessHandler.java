package dev.yoon.auth.handler;

import dev.yoon.auth.client.LikelionSsoConsts;
import dev.yoon.auth.service.PublisherService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@RequiredArgsConstructor
@Slf4j
public class CustomLogoutSuccessHandler implements LogoutSuccessHandler {

    private final PublisherService publisherService;

    @Override
    public void onLogoutSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication
    ) throws IOException, ServletException {

        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(LikelionSsoConsts.LIKELION_LOGIN_COOKIE)) {
                    System.out.println("publishing");
                    publisherService.publishMessage(cookie.getValue());
                    response.sendRedirect("http://localhost:9080");
                }
            }
        }

    }
}
