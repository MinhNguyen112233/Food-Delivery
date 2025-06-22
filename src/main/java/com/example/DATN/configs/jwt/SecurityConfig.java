//package com.example.DATN.configs.jwt;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.access.AccessDeniedException;
//import org.springframework.security.authentication.AuthenticationProvider;
//import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.security.web.access.AccessDeniedHandler;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//import org.springframework.web.cors.CorsConfiguration;
//import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
//import org.springframework.web.filter.CorsFilter;
//
//import java.io.IOException;
//import java.util.Map;
//
//@Configuration
//@EnableWebSecurity
//@EnableGlobalMethodSecurity(prePostEnabled = true)
//public class SecurityConfig {
//
//    private final AuthenticationProvider authenticationProvider;
//    private final JwtAuthenticationFilter jwtAuthenticationFilter;
//
//    // Constructor-based Dependency Injection
//    public SecurityConfig(AuthenticationProvider authenticationProvider,
//                          JwtAuthenticationFilter jwtAuthenticationFilter) {
//        this.authenticationProvider = authenticationProvider;
//        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
//    }
//
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http
//                .csrf(csrf -> csrf.disable())
//                .authorizeHttpRequests(auth -> auth
//                        // Public endpoints - không cần JWT
//                        .requestMatchers(
//                                "/auth/user/**",
//                                "/otp/**",
//                                "/file/**",
//                                "/foodShops/**",
//                                "/foods/**",
//                                "/reviews/get-reviews-by-food-id",
//                                "/categories/**",
//                                "/category-items/**",
//                                "/users/by_phone",
//                                "/users/by_email",
//                                "/users/update-new-password",
//                                "/users/update-user",
//                                "/auth/registerCheck",
//                                "/users/update-user-avatar"
//                        ).permitAll()
//
//                        // Tất cả các endpoint khác cần authentication
//                        .anyRequest().authenticated()
//                )
//                .sessionManagement(session -> session
//                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                )
//                .exceptionHandling(exception -> exception
//                        .accessDeniedHandler(customAccessDeniedHandler()) // custom handler
//                )
//                .authenticationProvider(authenticationProvider)
//                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
//
//        return http.build();
//    }
//
//    @Bean
//    public AccessDeniedHandler customAccessDeniedHandler() {
//        return new AccessDeniedHandler() {
//            private final ObjectMapper objectMapper = new ObjectMapper();
//
//            @Override
//            public void handle(HttpServletRequest request, HttpServletResponse response,
//                               AccessDeniedException accessDeniedException) throws IOException, ServletException {
//                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
//                response.setContentType("application/json;charset=UTF-8");
//
//                Map<String, Object> responseBody = Map.of(
//                        "status", 403,
//                        "message", "Bạn không có quyền truy cập vào tài nguyên này"
//                );
//
//                response.getWriter().write(objectMapper.writeValueAsString(responseBody));
//            }
//        };
//    }
//
//    @Bean
//    public CorsFilter corsFilter() {
//        CorsConfiguration corsConfiguration = new CorsConfiguration();
//
//        corsConfiguration.addAllowedOrigin("*");
//        corsConfiguration.addAllowedMethod("*");
//        corsConfiguration.addAllowedHeader("*");
//
//        UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
//        urlBasedCorsConfigurationSource.registerCorsConfiguration("/**", corsConfiguration);
//
//        return new CorsFilter(urlBasedCorsConfigurationSource);
//    }
//}

package com.example.DATN.configs.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.io.IOException;
import java.util.Map;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    private final AuthenticationProvider authenticationProvider;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    // Constructor-based Dependency Injection
    public SecurityConfig(AuthenticationProvider authenticationProvider,
                          JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.authenticationProvider = authenticationProvider;
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        // Public endpoints - không cần JWT
                        .requestMatchers(
                                "/auth/**",
                                "/otp/**",
                                "/file/**",
                                "/foodShops/**",
                                "/foods/**",
                                "/reviews/get-reviews-by-food-id",
                                "/categories/**",
                                "/category-items/**",
                                "/users/by_phone",
                                "/users/by_email",
                                "/users/update-new-password",
                                "/users/update-user",
                                "/auth/registerCheck",
                                "/users/update-user-avatar",
                                // WebSocket endpoints - QUAN TRỌNG: Thêm các endpoint WebSocket
                                "/ws/**",
                                "/ws",
                                "/ws-sockjs/**",
                                "/app/**",
                                "/topic/**",
                                "/queue/**",
                                "/user/**"
                        ).permitAll()

                        // Tất cả các endpoint khác cần authentication
                        .anyRequest().authenticated()
                )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .exceptionHandling(exception -> exception
                        .accessDeniedHandler(customAccessDeniedHandler()) // custom handler
                )
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public AccessDeniedHandler customAccessDeniedHandler() {
        return new AccessDeniedHandler() {
            private final ObjectMapper objectMapper = new ObjectMapper();

            @Override
            public void handle(HttpServletRequest request, HttpServletResponse response,
                               AccessDeniedException accessDeniedException) throws IOException, ServletException {
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                response.setContentType("application/json;charset=UTF-8");

                Map<String, Object> responseBody = Map.of(
                        "status", 403,
                        "message", "Bạn không có quyền truy cập vào tài nguyên này"
                );

                response.getWriter().write(objectMapper.writeValueAsString(responseBody));
            }
        };
    }

    @Bean
    public CorsFilter corsFilter() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();

        // Cấu hình CORS chi tiết hơn cho WebSocket
        corsConfiguration.setAllowCredentials(true);
        corsConfiguration.addAllowedOriginPattern("*"); // Sử dụng pattern thay vì "*" khi allowCredentials = true
        corsConfiguration.addAllowedMethod("*");
        corsConfiguration.addAllowedHeader("*");

        // Thêm headers cần thiết cho WebSocket
        corsConfiguration.addExposedHeader("*");

        UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
        urlBasedCorsConfigurationSource.registerCorsConfiguration("/**", corsConfiguration);

        return new CorsFilter(urlBasedCorsConfigurationSource);
    }
}