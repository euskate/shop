package com.example.shop.config;

import com.example.shop.service.MemberService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.security.config.http.MatcherType.ant;
import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final MemberService memberService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // CSRF 응답 관련 세션 생성
        http.sessionManagement(sessionManagementConfigurer ->
                        sessionManagementConfigurer
                        .sessionCreationPolicy(SessionCreationPolicy.ALWAYS));

        // 로그인
        http.formLogin(formLoginConfigurer ->
                formLoginConfigurer
                        .loginPage("/members/login")
                        .defaultSuccessUrl("/")
                        .usernameParameter("email")
                        .failureUrl("/members/login/error"));

        // 로그아웃
        http.logout(logoutConfigurer ->
                logoutConfigurer
                        .logoutRequestMatcher(new AntPathRequestMatcher("/members/logout"))
                        .logoutSuccessUrl("/"));

        // 인증
        http
            .authorizeHttpRequests(authorization -> authorization
                //.requestMatchers(new AntPathRequestMatcher("/h2-console/**")).permitAll() // H2 Console
                .requestMatchers(new AntPathRequestMatcher("/")).permitAll()
                .requestMatchers(new AntPathRequestMatcher("/css/**")).permitAll()
                .requestMatchers(new AntPathRequestMatcher("/members/**")).permitAll()
                .requestMatchers(antMatcher("/item/**")).permitAll()
                .requestMatchers(antMatcher("/images/**")).permitAll()
                .requestMatchers(new AntPathRequestMatcher("/admin/**")).hasRole("ADMIN")
                .anyRequest().authenticated());     // 어떠한 요청이라도 인증 필요

//        http.csrf(httpSecurityCsrfConfigurer -> httpSecurityCsrfConfigurer
//                .ignoringRequestMatchers("/h2.console/**"));
//
        // 인증되지 않은 사용자가 들어왔을 때 예외 처리
        http.exceptionHandling(
                exception -> exception.authenticationEntryPoint(new AuthenticationEntryPoint() {
                    @Override
                    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
                        response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
                    }
                })
        );

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


}
