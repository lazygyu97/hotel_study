package com.sparta.hotel.controller;

import com.sparta.hotel.jwt.JwtUtil;
import com.sparta.hotel.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Slf4j(topic = "UserController")
@Controller
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserController {

  private final UserService userService;
  private final JwtUtil jwtUtil;

  @GetMapping("/user/login-page")
  public String loginPage() {
    return "login";
  }

  @GetMapping("/user/signup")
  public String signupPage() {
    return "signup";
  }

}
