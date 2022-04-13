package dev.yoon.challenge_community.config;

import dev.yoon.challenge_community.LikelionSsoConsts;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers(
                        "/home/**",
                        "/",
                        "/css/**",
                        "/images/**",
                        "/js/**"
                )
                .permitAll()
                .anyRequest()
                .authenticated()
                .and()
//                .csrf().disable()
//                .authorizeRequests(authorizeRequests -> {
//                    authorizeRequests.antMatchers(
//                            "/home/**",
//                            "/",
//                            "/css/**",
//                            "/images/**",
//                            "/js/**"
//                            ).permitAll();
//                    authorizeRequests.anyRequest().authenticated();
//                })
//                .formLogin(formLogin-> {
//                    formLogin.loginPage("/user/login");
//                })
                .logout(logout -> {
                    logout.logoutUrl("/user/logout");
                    logout.logoutSuccessUrl("/home");
                    logout.deleteCookies(LikelionSsoConsts.LIKELION_LOGIN_COOKIE);
                    logout.invalidateHttpSession(true);
                    logout.permitAll();
                });
    }

}
