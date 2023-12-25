package com.example.librarysystem.config;

import com.example.librarysystem.service.jwt.AuthenticationService;
import com.example.librarysystem.service.jwt.JwtFilter;
import com.example.librarysystem.service.jwt.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity
public class SpringConfig {
    private final JwtService jwtService;
    private final AuthenticationService authenticationService;


    private final String[] WHITE_LIST = {
            "/user/sign-up",
            "/user/sign-in",
            "/user/verify",
            "/extract-token",
            "/user/extract-token",
            "/user/generate-token",
            "/user/get-verify-code",
            "/book/get-with-floor-and-shelf",
            "/book/borrow",
            "/email/**"

    };

    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(requestConfigurer -> {
                    requestConfigurer
                            .requestMatchers( WHITE_LIST).permitAll()
                            .anyRequest().authenticated();
                })
                .addFilterBefore(new JwtFilter(jwtService, authenticationService),
                        UsernamePasswordAuthenticationFilter.class)
                .sessionManagement(sessionManagement -> {
                    sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
                })
//                .cors(httpSecurityCorsConfigurer ->
//                {
//                    CorsConfiguration configuration = new CorsConfiguration();
//                    configuration.setAllowedOrigins(Arrays.asList("*"));
//                    configuration.setAllowedMethods(Arrays.asList("*"));
//                    configuration.setAllowedHeaders(Arrays.asList("*"));
//
//                    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//                    source.registerCorsConfiguration("/**", configuration);
//                    httpSecurityCorsConfigurer.configurationSource(source);
//                })
                .build();
    }

}
