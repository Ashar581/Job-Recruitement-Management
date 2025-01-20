package com.ashar.job.recruitment.management.Config;

import com.ashar.job.recruitment.management.Security.JwtValidatorFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.Collections;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
public class AppSecurityConfig {
    @Autowired
    private JwtValidatorFilter validatorFilter;
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{
        httpSecurity.csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .cors(cors -> cors.configurationSource(config -> {
                    CorsConfiguration configuration = new CorsConfiguration();
                    configuration.setAllowedHeaders(Arrays.asList("Authorization","Content-Type","Accept","Origin"));
                    configuration.setAllowedMethods(Arrays.asList("POST","PUT","PATCH","DELETE","GET"));
                    configuration.setAllowedOrigins(Collections.singletonList("*"));
                    configuration.setExposedHeaders(Arrays.asList("Authorization"));

                    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
                    source.registerCorsConfiguration("/api/**",configuration);

                    return configuration;
                }))
                .addFilterAfter(validatorFilter, BasicAuthenticationFilter.class)
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/api/auth/login","/api/users/create","/api/job/all","/api/candidate/apply").permitAll()
                        .requestMatchers(HttpMethod.POST,"/api/roles/create").hasAuthority("ADMIN")
                        .anyRequest().authenticated())
                .httpBasic(withDefaults());

        return httpSecurity.build();
    }

}
