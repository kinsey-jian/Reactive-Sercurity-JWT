package com.kinsey.oauth.controller;

import com.kinsey.oauth.dto.UserRequest;
import com.kinsey.oauth.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

/**
 * Created by zj on 2019/1/13
 */
@RestController
@AllArgsConstructor
@RequestMapping("/authorize")
public class UserController {

    private final UserService userService;

    @PostMapping
    public Mono<String> login(@Valid @RequestBody UserRequest request) {
        return userService.login(request);
    }

    @PostMapping("/user")
    public Mono<Void> createUser(@RequestBody UserRequest request){
        return userService.createUser(request);
    }
}
