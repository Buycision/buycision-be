package project.gatewayservice.auth.handler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Component;
import project.gatewayservice.auth.service.JwtProvider;
import project.gatewayservice.auth.service.dto.BearerToken;
import reactor.core.publisher.Mono;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationManager implements ReactiveAuthenticationManager {

    private final JwtProvider jwtProvider;

    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {
//        log.info("JwtAuthenticationManager.authenticate: {}", authentication.getPrincipal());

        if (authentication.getPrincipal().equals("Guest")) {
            return Mono.just(new AnonymousAuthenticationToken("Guest", "Guest", AuthorityUtils.createAuthorityList("ROLE_GUEST")));
        }

        return Mono.justOrEmpty(authentication)
                .filter(auth -> auth instanceof BearerToken)
                .cast(BearerToken.class)
                .flatMap(this::validateToken);
    }

    private Mono<Authentication> validateToken(BearerToken bearerToken) {
        String token = bearerToken.getValue();
        if (!jwtProvider.validateToken(token)) {
            return Mono.empty();
        }
        String userId = jwtProvider.decodeToken(token);

        BearerToken authentication = BearerToken.of(userId);
        authentication.setAuthenticated(true);

        return Mono.just(authentication);
    }
}
