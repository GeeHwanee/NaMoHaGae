package kr.kro.namohagae.global.config;

import jakarta.servlet.DispatcherType;
import kr.kro.namohagae.global.security.LoginFailHandler;
import kr.kro.namohagae.global.security.LoginSuccessHandler;
import kr.kro.namohagae.global.security.MyAccessDeniedHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@RequiredArgsConstructor
@EnableMethodSecurity(securedEnabled = true)
@EnableWebSecurity
@Configuration
public class SecurityConfig {

    private final MyAccessDeniedHandler myAccessDeniedHandler;
    private final LoginSuccessHandler loginSuccessHandler;
    private final LoginFailHandler loginFailHandler;
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(request -> request
                .dispatcherTypeMatchers(DispatcherType.FORWARD).permitAll()
                .requestMatchers("/login","/puching","/chatroom","/api/v1/**","/member/join","/member/addJoin","/member/kakaoJoin","/member/find","/css/**","/script/**").permitAll()
                .anyRequest().authenticated());

        http.formLogin().loginPage("/login")
                .loginProcessingUrl("/api/v1/login").defaultSuccessUrl("/",true)
                .successHandler(loginSuccessHandler)
                .failureHandler(loginFailHandler);
        http.logout().logoutUrl("/logout").logoutSuccessUrl("/");
        http.exceptionHandling().accessDeniedHandler(myAccessDeniedHandler);

        return http.build();
    }

}
