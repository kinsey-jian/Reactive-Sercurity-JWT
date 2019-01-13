package com.kinsey.oauth.service;

import com.google.common.collect.Sets;
import com.kinsey.oauth.data.User;
import com.kinsey.oauth.data.repository.UserRepository;
import com.kinsey.oauth.dto.UserRequest;
import com.kinsey.oauth.enums.RoleEnum;
import com.kinsey.oauth.jwt.JWTReactiveAuthenticationManager;
import com.kinsey.oauth.jwt.TokenProvider;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

/**
 * Created by zj on 2019/1/13
 */
@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;
    private final JWTReactiveAuthenticationManager jwtReactiveAuthenticationManager;

    public Mono<String> login(UserRequest request) {
        Authentication authenticationToken =
                new UsernamePasswordAuthenticationToken(request.getUserName(), request.getPassword());

        Mono<Authentication> authentication = this.jwtReactiveAuthenticationManager.authenticate(authenticationToken);
        ReactiveSecurityContextHolder.withAuthentication(authenticationToken);

        return authentication.map(tokenProvider::createToken);
    }

    public Mono<Void> createUser(UserRequest request) {
        User user = new User();
        user.setUserName(request.getUserName());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRoles(Sets.newHashSet(RoleEnum.ROLE_ADMIN));
        userRepository.save(user);
        return Mono.empty();
    }
}
