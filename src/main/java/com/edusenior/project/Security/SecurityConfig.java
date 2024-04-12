package com.edusenior.project.Security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private JWTAuthenticationFilter jwtAuthenticationFilter;

    @Autowired
    public SecurityConfig(JWTAuthenticationFilter jwt){
        this.jwtAuthenticationFilter = jwt;
    }
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        //NOT NEEDED CUSTOM JWT FILTER BEING USED

//        http.authorizeHttpRequests(auth -> auth.
//                requestMatchers(HttpMethod.POST,"/login").permitAll().
//                requestMatchers("/students/register").permitAll().
//                requestMatchers("/teachers/register").permitAll());
//
//        http.httpBasic(Customizer.withDefaults());
        http.csrf(CsrfConfigurer::disable);
        return http.build();
    }


}