package com.korea.it.shopping.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    // 비밀번호 암호화 설정
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors()  // CORS 허용 추가
                .and()
                .csrf().disable()  // CSRF 보호 비활성화
                .authorizeRequests()

                //페이지 권한 처리
                .antMatchers("/api/auth/join", "/api/auth/login").permitAll()  // 회원가입과 로그인은 누구나 접근 가능
                .antMatchers("/cart/**", "/upload/**", "/api/auth/profile").authenticated()  // 장바구니, 상품등록은 로그인 필요
                .anyRequest().authenticated()  // 그 외의 요청은 인증 필요

                .and()

                //로그인 처리
                .formLogin()
                .loginPage("/login").permitAll()  // 커스텀 로그인 페이지
                .permitAll()
                .and()

                //로그아웃 처리
                .logout()
                .logoutUrl("/api/auth/logout")
                .logoutSuccessUrl("/login")  // 로그아웃 성공 후 리다이렉트할 경로
                .permitAll();  // 로그아웃은 누구나 접근 가능

    }

}
