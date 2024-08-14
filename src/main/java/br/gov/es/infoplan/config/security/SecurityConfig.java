package br.gov.es.infoplan.config.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final ClientRegistrationRepository clientRegistrationRepository;
    private final SecurityFilter securityFilter;
    private final CustomAccessDeniedHandler customAccessDeniedHandler;

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(STATELESS))
                .authorizeHttpRequests(authConfig -> {
                    authConfig.requestMatchers(HttpMethod.GET,
                            "/swagger-ui.html",
                            "/swagger-ui/*",
                            "/v3/*",
                            "/v3/api-docs/*",
                            "/signin/*",
                            "/acesso-cidadao-response.html").permitAll();
                    authConfig.anyRequest().authenticated();
                })
                .oauth2Login(oAuth2LoginConfig ->
                        oAuth2LoginConfig.authorizationEndpoint(authEndpointConfig ->
                                authEndpointConfig.authorizationRequestResolver(new AuthorizationRequestResolver(
                                        clientRegistrationRepository, "/oauth2/authorization")))
                )
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling(exHandler -> exHandler.accessDeniedHandler(customAccessDeniedHandler))
                .build();
    }
}