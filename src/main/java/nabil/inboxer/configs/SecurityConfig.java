package nabil.inboxer.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

/**
 * @author Ahmed Nabil
 */
@Configuration
public class SecurityConfig {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
       return http
                .authorizeHttpRequests(auth ->
                        auth
                                .requestMatchers("/login", "/error").permitAll()
                                .anyRequest().authenticated()
                )
//                .exceptionHandling(e -> e
//                        .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))
//                )
                .csrf(c -> c
                        .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                )
               .oauth2Login(config -> config.loginPage("/login")).build();
    }


    @Bean
    public Authentication authentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }
}
