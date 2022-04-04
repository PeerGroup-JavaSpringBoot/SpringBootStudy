package dev.yoon.challenge_community.filter;

import dev.yoon.challenge_community.LikelionSsoConsts;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Principal;
import java.util.Collection;
import java.util.Collections;

@Slf4j
@Component
@Order(value = 1)
public class CookieFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;

        Cookie[] cookies = httpServletRequest.getCookies();

        /**
         * 같은 도메인에서 쿠키를 생성하여서
         * 쿠키가 생성안됨
         * 그래서 한쪽은 127.0.0~/ 한쪽은 localhost로 접근
         */
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if(cookie.getName().equals(LikelionSsoConsts.LIKELION_LOGIN_COOKIE)) {
                    log.info("Login Token Found, {}", cookie.getValue());

                    System.out.println("==============CookieFilter In=================");
                    chain.doFilter(httpServletRequest, httpServletResponse);
                    System.out.println("==============CookieFilter Out=================");
                    return;
                }
            }
        }

        System.out.println("==============CookieFilter Out=================");
        log.info("Login Token Missing");
        chain.doFilter(httpServletRequest, httpServletResponse);

    }


}
