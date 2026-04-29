package br.gov.es.infoplan.config.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.core.annotation.Order;
import br.gov.es.infoplan.service.TokenService;
import br.gov.es.infoplan.service.AutenticacaoService;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;
import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

        private final ClientRegistrationRepository clientRegistrationRepository;
        private final CustomAccessDeniedHandler customAccessDeniedHandler;
        private final TokenService tokenService;
        private final AutenticacaoService authSrv;

        @Bean
        public SecurityFilter securityFilter() {
                return new SecurityFilter(tokenService, authSrv);
        }

        @Bean
        public WebSecurityCustomizer webSecurityCustomizer() {
                return (web) -> web.ignoring().requestMatchers(
                                antMatcher("/**/swagger-ui/**"),
                                antMatcher("/**/v1/api-docs/**"),
                                antMatcher("/**/swagger-ui.html"),
                                antMatcher("/**/signin/**"),
                                antMatcher("/**/acesso-cidadao-response.html"));
        }

        @Bean
        @Order(1)
        SecurityFilterChain publicSecurityFilterChain(HttpSecurity http) throws Exception {
                return http
                                .securityMatchers(matchers -> matchers
                                                .requestMatchers(
                                                                antMatcher("/**/swagger-ui/**"),
                                                                antMatcher("/**/v1/api-docs/**"),
                                                                antMatcher("/**/swagger-ui.html"),
                                                                antMatcher("/**/signin/**"),
                                                                antMatcher("/**/acesso-cidadao-response.html")))
                                .csrf(AbstractHttpConfigurer::disable)
                                .sessionManagement(session -> session.sessionCreationPolicy(STATELESS))
                                .authorizeHttpRequests(auth -> auth.anyRequest().permitAll())
                                .build();
        }

        @Bean
        @Order(2)
        SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
                return http
                                .csrf(AbstractHttpConfigurer::disable)
                                .sessionManagement(session -> session.sessionCreationPolicy(STATELESS))
                                .authorizeHttpRequests(authConfig -> {
                                        authConfig.requestMatchers(
                                                        antMatcher("/**/swagger-ui/**"),
                                                        antMatcher("/**/v1/api-docs/**"),
                                                        antMatcher("/**/swagger-ui.html"),
                                                        antMatcher("/**/signin/**")).permitAll();
                                        authConfig.anyRequest().authenticated();
                                })
                                .oauth2Login(oAuth2LoginConfig -> oAuth2LoginConfig.authorizationEndpoint(
                                                authEndpointConfig -> authEndpointConfig.authorizationRequestResolver(
                                                                new AuthorizationRequestResolver(
                                                                                clientRegistrationRepository,
                                                                                "/oauth2/authorization"))))
                                .addFilterBefore(securityFilter(), UsernamePasswordAuthenticationFilter.class)
                                .exceptionHandling(exHandler -> exHandler
                                                .authenticationEntryPoint(
                                                                new org.springframework.security.web.authentication.HttpStatusEntryPoint(
                                                                                org.springframework.http.HttpStatus.UNAUTHORIZED))
                                                .accessDeniedHandler(customAccessDeniedHandler))
                                .build();
        }
}