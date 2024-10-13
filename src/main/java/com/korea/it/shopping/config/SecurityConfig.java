package com.korea.it.shopping.config;

import com.korea.it.shopping.users.service.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final CustomUserDetailsService customUserDetailsService;

    public SecurityConfig(CustomUserDetailsService customUserDetailsService) {
        this.customUserDetailsService = customUserDetailsService;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customUserDetailsService).passwordEncoder(passwordEncoder());
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors() // CORS 설정 허용
                .and()
                .csrf().disable()
                // URL별 권한 설정
                .authorizeRequests()

                // 정적 리소스는 인증 없이 접근 가능
                .antMatchers("/images/**", "/css/**", "/js/**").permitAll()

                .antMatchers("/login/","/api/auth/join", "/api/auth/login",
                        "/api/auth/profile","/api/products/**","/api/notices","/socket.io"
                        ,"api/notices/**","/notices/**").permitAll() // 회원가입, 로그인은 모두 접근 가능

                // 상품 등록 및 장바구니는 로그인한 사용자만 접근 가능
                .antMatchers("/cart").authenticated()  // 장바구니에 접근하려면 인증 필요
                .antMatchers("/api/products/upload").authenticated()  // 상품 등록에 접근하려면 인증 필요
                .antMatchers("/upload").authenticated()  // 상품 등록에 접근하려면 인증 필요


                .anyRequest().authenticated() // 그 외 모든 요청은 인증 필요

                .and()
                .formLogin().disable()

                // 로그아웃 설정
                .logout()
                .logoutUrl("/logout") // 로그아웃 요청 처리 경로
                .logoutSuccessUrl("/") // 로그아웃 성공 시 리다이렉트 경로
                .permitAll() // 로그아웃 요청은 모두 접근 가능
                .and()

                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)  // 항상 세션을 생성하여 유지
                .maximumSessions(1);  // 동시에 하나의 세션만 허용
    }


}
