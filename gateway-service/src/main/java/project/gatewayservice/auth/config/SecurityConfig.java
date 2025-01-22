package project.gatewayservice.auth.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authentication.AuthenticationWebFilter;
import org.springframework.security.web.server.context.NoOpServerSecurityContextRepository;
import org.springframework.security.web.server.header.XFrameOptionsServerHttpHeadersWriter;
import project.gatewayservice.auth.handler.AuthenticationEntryPoint;
import project.gatewayservice.auth.handler.AuthenticationSuccessHandler;
import project.gatewayservice.auth.handler.JwtAuthenticationManager;
import project.gatewayservice.auth.handler.JwtServerAuthenticationConverter;

@Configuration
@EnableWebFluxSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationManager jwtAuthenticationManager;
    private final JwtServerAuthenticationConverter jwtServerAuthenticationConverter;
    private final AuthenticationSuccessHandler authenticationSuccessHandler;
    private final AuthenticationEntryPoint authenticationEntryPoint;

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        AuthenticationWebFilter authenticationWebFilter = new AuthenticationWebFilter(jwtAuthenticationManager);
        authenticationWebFilter.setServerAuthenticationConverter(jwtServerAuthenticationConverter);

        return http
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .httpBasic(ServerHttpSecurity.HttpBasicSpec::disable)
                .formLogin(ServerHttpSecurity.FormLoginSpec::disable)

                .headers(headers -> headers
                        .frameOptions(opt -> opt.mode(XFrameOptionsServerHttpHeadersWriter.Mode.SAMEORIGIN)))

                .securityContextRepository(NoOpServerSecurityContextRepository.getInstance()) // Stateless for JWT

                .authorizeExchange(authorize -> authorize
                        .pathMatchers("/", "/login/**").permitAll()
                        .pathMatchers("/swagger-ui.html", "/webjars/swagger-ui/**").permitAll()
                        .pathMatchers("/v3/api-docs/**", "/user/v3/api-docs", "/chat/v3/api-docs", "/community/v3/api-docs").permitAll()
                        .pathMatchers("/user/email/**", "/user/register").permitAll() // for Auth
                        .pathMatchers("/chat/**").permitAll()
                        .pathMatchers("/h2-console/**").permitAll()
                        .anyExchange().permitAll()
                )
                .logout(logout -> logout.logoutUrl("/logout"))
                .oauth2Login(auth -> auth.authenticationSuccessHandler(authenticationSuccessHandler))
                .addFilterAt(authenticationWebFilter, SecurityWebFiltersOrder.AUTHENTICATION)
                .exceptionHandling(exceptionHandling -> exceptionHandling.authenticationEntryPoint(authenticationEntryPoint))
                .build();
    }
}
