package com.dailyon.memeberservice.member.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
            .authorizeRequests()
                .antMatchers("*").permitAll() // 모든 사용자에게 허용
               //.anyRequest().authenticated() // 나머지 요청은 인증된 사용자에게만 허용
                .and()
                .formLogin().disable(); // 폼 기반 로그인 비활성화
    }
}
