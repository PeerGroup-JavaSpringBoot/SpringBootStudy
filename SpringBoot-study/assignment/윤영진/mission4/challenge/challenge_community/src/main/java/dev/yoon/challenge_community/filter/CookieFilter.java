package dev.yoon.challenge_community.filter;

import lombok.extern.slf4j.Slf4j;
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
public class CookieFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;

        Cookie[] cookies = httpServletRequest.getCookies();

        if (cookies != null) {
            for (int i = 0; i < cookies.length; i++) {
                if (cookies[i].getName().equals("likelion_login_cookie")) {
                    log.info("Cookie Found");
                    String queryString = httpServletRequest.getQueryString();
                    if(queryString!=null) {
                        String value = queryString.split("likelion_login_cookie=")[1];
                        Cookie cookie = new Cookie("likelion_login_cookie", value);
                        cookie.setPath("/");
                        httpServletResponse.addCookie(cookie);
                        setAuthentication();
                    }
                    break;
                }
                if (i == cookies.length - 1) {
                    log.info("Cookie Not Found");
                }
            }
        }

        chain.doFilter(httpServletRequest, httpServletResponse);

    }

    private void setAuthentication() {
        SecurityContextHolder.getContext().setAuthentication(new Authentication() {
            @Override
            public Collection<? extends GrantedAuthority> getAuthorities() {
                return Collections.emptyList();
            }
            @Override
            public Object getCredentials() {
                return null;
            }

            @Override
            public Object getDetails() {
                return null;
            }

            @Override
            public Object getPrincipal() {
                return (Principal) () -> "dummy";
            }

            @Override
            public boolean isAuthenticated() {
                return true;
            }

            @Override
            public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
            }

            @Override
            public String getName() {
                return "dummy";
            }
        });
    }
}
