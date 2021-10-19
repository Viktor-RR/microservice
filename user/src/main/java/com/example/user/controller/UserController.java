package com.example.user.controller;


import com.example.user.dto.FinalResponseDataDto;
import com.example.user.dto.UserLoginDto;
import com.example.user.dto.UserRegisterDto;
import com.example.user.dto.ResponseDataDto;
import com.example.user.entity.User;
import com.example.user.service.UserService;
import com.nimbusds.jose.shaded.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;


@RestController
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/usersPayments")
    public List<FinalResponseDataDto> getAllUsersByPayments(@RequestBody List<ResponseDataDto> payments) {
        return userService.mapAllUsersWithPayments(payments);
    }

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody UserRegisterDto userRegisterDto){
        final User user = userService.saveUser(userRegisterDto);
        return new ResponseEntity<>(user, HttpStatus.OK);

    }

    @PostMapping("/login")
    public ResponseEntity<JSONObject> login(@RequestBody UserLoginDto userLoginDto) {
        final String token = userService.login(userLoginDto);
        final JSONObject jsonObject = new JSONObject(Map.of("Jwt-Token", token));
        return new ResponseEntity<>(jsonObject, HttpStatus.OK);
    }
}
