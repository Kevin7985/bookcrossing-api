package ru.ist.service;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import ru.ist.users.UserRepository;
import ru.ist.users.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class AuthService {
    private static final String BEARER_PREFIX = "Bearer ";
    private final UserRepository userRepository;
    private final RedisTemplate<String, String> redis;

    public Optional<Authentication> authenticate(HttpServletRequest req) {
        return extractToken(req).flatMap(this::checkToken);
    }

    private Optional<String> extractToken(HttpServletRequest req) {
        try {
            String token = req.getHeader(HttpHeaders.AUTHORIZATION);
            if (token != null) {
                if (token.startsWith(BEARER_PREFIX)) {
                    token = token.substring(BEARER_PREFIX.length()).trim();
                    if (!token.isBlank()) {
                        return Optional.of(token);
                    }
                }
            }
        } catch (Exception e) {
            return Optional.empty();
        }

        return Optional.empty();
    }

    private Optional<Authentication> checkToken(String token) {
        try {
            String userId = redis.opsForValue().get("auth_token_" + token);
            if (userId != null) {
                List<Role> roles = new ArrayList<>(List.of(Role.USER));
                Authentication authentication = createAuth(userId, roles);
                return Optional.of(authentication);
            }
        } catch (Exception e) {
            return Optional.empty();
        }

        return Optional.empty();
    }

    private static Authentication createAuth(String user, List<Role> roles) {
        List<GrantedAuthority> authorities = roles.stream()
                .distinct()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                .collect(toList());
        return new UsernamePasswordAuthenticationToken(user, "N/A", authorities);
    }

    private enum Role {
        USER
    }
}
