package eci.server.config.security;

import eci.server.ItemModule.exception.member.auth.AccessExpiredException;
import eci.server.ItemModule.service.sign.TokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends GenericFilterBean {

    /**
     * 1) Authorization 헤더에서 토큰 값을 꺼냄
     *
     * 2) 핵심 기능 : 액세스 토큰이 유효할 때만, SpringSecurity 관리해주는 컨텍스트에 사용자 정보 저장
     *
     * 사용자 정보(CustomAuthenticationToken) 등록
     * == SecurityContextHolder에 있는 ContextHolder에
     * Authentication 인터페이스의 구현체 CustomAuthenticationToken 등록
     *
     */

    private final TokenService tokenService;
    private final CustomUserDetailsService userDetailsService;


    private final Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

    /*
    CORS 처리를 위한 Filter는
    반드시 인증 처리하는
    Filter 이전에 있어야 한다.
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletResponse response1 = (HttpServletResponse) response;
        HttpServletRequest request1 = (HttpServletRequest) request;

        response1.setHeader("Access-Control-Allow-Origin", "https://localhost:3000");
        response1.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
        response1.setHeader("Access-Control-Max-Age", "3600");
        response1.setHeader("Access-Control-Allow-Headers", "Content-Type, Accept, X-Requested-With, remember-me, Origin,Content-Type,Access-Control-Request-Method,Access-Control-Request-Headers,Authorization");
        response1.setHeader("Access-Control-Allow-Credentials",  "true");

        String token = extractToken(request);

        if(validateToken(token)) {
            // SecurityContext에 Authentication 객체 저장
            setAuthentication(token);
        }

        chain.doFilter(request, response1);
 }



    private String extractToken(ServletRequest request) {
        return ((HttpServletRequest)request).getHeader("Authorization");
    }

    private boolean validateToken(String token) {
        return(token != null && tokenService.validateAccessToken(token));
    }

    private void setAuthentication(String token) {
        String userId = tokenService.extractAccessTokenSubject(token);
        if(userId == null){

            throw new AccessExpiredException();

        }

        // 토큰에서 빼 온 유저의 아이디 값으로 UserDetails 빼오고, Authentication 토큰 만드는 것
        // 따라서 나는 authentication 토큰을 만드는
        CustomUserDetails userDetails = userDetailsService.loadUserByUsername(userId);
        SecurityContextHolder.getContext().setAuthentication(
                new CustomAuthenticationToken(
                        userDetails, userDetails.getAuthorities()
                )
        );
    }

}