package dev.yoon.auth.config;

import dev.yoon.auth.handler.CustomLogoutSuccessHandler;
import dev.yoon.auth.handler.CustomSuccessHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final CustomSuccessHandler customSuccessHandler;
    private final CustomLogoutSuccessHandler customLogoutSuccessHandler;


    /**
     * 편법
     * request-login path는 로그인이 필요한 경로
     * 스프링부트 내부에 정의가 된 형태로 들어오면
     * 마지막으로 들어온 요청을 저장
     * 요청 저장 후 로그인 페이지로 redirectx
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests(authorizeRequests -> {
                    authorizeRequests.antMatchers(
                            "/current-user/**",
                            "/home/**",
                            "/user/signup/**",
                            "/",
                            "/css/**",
                            "/images/**",
                            "/js/**"
                    ).permitAll();
//                    authorizeRequests.antMatchers("/request-login/**").permitAll();
                    authorizeRequests.anyRequest().authenticated();
                })
                .formLogin(formLogin -> {
                    formLogin.loginPage("/user/login");
//                    formLogin.loginPage("/request-login");
                    formLogin.successHandler(customSuccessHandler);
                    formLogin.permitAll();
                })
                .logout(logout -> {
                    logout.logoutUrl("/logout");
                    logout.logoutSuccessHandler(customLogoutSuccessHandler);
                    logout.permitAll();
                })
                ;
    }


}
