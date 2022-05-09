package eci.server.config.security;

import eci.server.ItemModule.service.sign.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final TokenService tokenService;
    private final CustomUserDetailsService userDetailsService;
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers(HttpMethod.OPTIONS, "/**");
        super.configure(web);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        /**
         * 로그인, 회원가입은 누구나
         * 회원정보 가져오는 것은 누구나
         * 멤버 삭제는 관리자 혹은 해당 멤버만
         */
        http
                .csrf().disable()
                .httpBasic().disable()
                .formLogin().disable()
                .authorizeRequests()

                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.POST, "/sign-in", "/sign-up", "/refresh-token").permitAll()

                .antMatchers(HttpMethod.GET, "/test").permitAll()
                .antMatchers(HttpMethod.GET, "/route/**").permitAll()
                .antMatchers(HttpMethod.DELETE, "/members/{id}/**").access("@memberGuard.check(#id)")
                .antMatchers(HttpMethod.GET, "/image/**").permitAll()

                .antMatchers(HttpMethod.POST, "/items").authenticated()


                .antMatchers(HttpMethod.GET, "/**").permitAll()//위에 명시된 get 말고는 다 허용, 맨 밑으로 위치 변경

                .anyRequest().hasAnyRole("ADMIN")//멤버의 역할이 관리자인 경우에는 모든 것을 허용

                .and()
                //컨트롤러 계층 도달 전 수행
                .exceptionHandling().authenticationEntryPoint(new CustomAuthenticationEntryPoint())
                .and()//인증되지 않은 사용자의 접근이 거부
                .exceptionHandling().accessDeniedHandler(new CustomAccessDeniedHandler())
                .and()//인증된 사용자가 권한 부족 등의 사유로 인해 접근이 거부

                //UsernamePasswordAuthenticationFilter 필터 이전에 JwtAuthentication 필터를 적용해라
                .addFilterBefore(new JwtAuthenticationFilter(tokenService, userDetailsService), UsernamePasswordAuthenticationFilter.class);

        http.headers().frameOptions().sameOrigin();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    }
