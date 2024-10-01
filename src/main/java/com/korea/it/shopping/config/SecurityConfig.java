package com.korea.it.shopping.config;

import com.korea.it.shopping.users.service.CustomUserDetailService;
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

    private final CustomUserDetailService customUserDetailService;

    public SecurityConfig(CustomUserDetailService customUserDetailService) {
        this.customUserDetailService = customUserDetailService;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customUserDetailService).passwordEncoder(passwordEncoder());
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
                .antMatchers("/api/auth/join", "/api/auth/login","/api/auth/profile").permitAll() // 회원가입, 로그인은 모두 접근 가능
                .anyRequest().authenticated() // 그 외 모든 요청은 인증 필요

                .and()
                .formLogin().disable()
//                .loginPage("/login").permitAll() //커스텀 로그인 페이지 경로 설정
//                .loginProcessingUrl("/login") //로그인 요청을 처리할 URL
//                .usernameParameter("email") //id파라미터를 email로 설정
//                .passwordParameter("password") //password파라미터 설정
//                .defaultSuccessUrl("/") //로그인 성공 시 리다이렉트할 경로
//                .permitAll()
//                .and()

                // 로그아웃 설정
                .logout()
                .logoutUrl("/api/auth/logout") // 로그아웃 요청 처리 경로
                .logoutSuccessUrl("/login") // 로그아웃 성공 시 리다이렉트 경로
                .permitAll() // 로그아웃 요청은 모두 접근 가능
                .and() // logout 블록 종료

                // 세션 관리 설정
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED) // 세션이 필요할 때만 생성
                .maximumSessions(1); // 동시에 하나의 세션만 허용
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:3000")); // 프론트엔드 포트 설정
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowCredentials(true);  // 쿠키를 허용
        configuration.setAllowedHeaders(List.of("Authorization", "Cache-Control", "Content-Type"));
        configuration.setExposedHeaders(List.of("Authorization", "Content-Type"));


        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }
}
