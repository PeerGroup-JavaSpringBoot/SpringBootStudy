package dev.yoon.challenge_community.config;

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
                .csrf().disable()
                .authorizeRequests(authorizeRequests -> {
                    authorizeRequests.antMatchers("/user/**", "/home/**").permitAll();
                    authorizeRequests.anyRequest().authenticated();
                })
                .formLogin(formLogin-> {
                    formLogin.loginPage("/user/login");
                })
                .logout(logout -> {
                    logout.logoutUrl("/user/logout");
                    logout.logoutSuccessUrl("/");
                    logout.invalidateHttpSession(true);
                });
    }

}
