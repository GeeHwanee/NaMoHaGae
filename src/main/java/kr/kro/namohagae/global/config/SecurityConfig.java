package kr.kro.namohagae.global.config;

import kr.kro.namohagae.global.security.LoginFailHandler;
import kr.kro.namohagae.global.security.LoginSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@EnableMethodSecurity(securedEnabled = true)
@EnableWebSecurity
@Configuration
public class SecurityConfig {
    @Autowired
    private LoginSuccessHandler loginSuccessHandler;
    @Autowired
    private LoginFailHandler loginFailHandler;
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.formLogin().loginPage("/login")
                .loginProcessingUrl("/api/v1/login")
                .successHandler(loginSuccessHandler)
                .failureHandler(loginFailHandler);
        http.logout().logoutUrl("/api/v1/logout").logoutSuccessUrl("/");

        return http.build();
    }

}
