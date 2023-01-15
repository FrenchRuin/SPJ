package com.example.spj.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.web.SecurityFilterChain;

public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .csrf().disable()
//                .authorizeRequests().anyRequest().permitAll()
                .formLogin().loginPage("/loginForm").defaultSuccessUrl("/",false)
                .and().logout().logoutUrl("/logout").logoutSuccessUrl("/login")
                .and()
                .build();

    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return web -> web.ignoring().antMatchers("/static/**");
    }


}
