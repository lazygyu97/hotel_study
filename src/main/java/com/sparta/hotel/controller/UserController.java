package com.sparta.hotel.controller;

import com.sparta.hotel.dto.ApiResponseDto;
import com.sparta.hotel.dto.SignupRequestDto;
import com.sparta.hotel.dto.UsernameRequestDto;
import com.sparta.hotel.service.UserService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Slf4j(topic = "UserController")
@Controller
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserController {

    private final UserService userService;
    @ExceptionHandler
    public ResponseEntity<ApiResponseDto> handleException(IllegalArgumentException ex){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponseDto(ex.getMessage()));
    }
    @GetMapping("/user/login-page")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/user/signup-page")
    public String signupPage() {
        return "signup";
    }
    @PostMapping("/user/signup/username")
    @ResponseBody
    public ResponseEntity<ApiResponseDto> checkUsername(@RequestBody UsernameRequestDto requestDto){
        return ResponseEntity.ok().body(userService.checkUsername(requestDto));
    }

    @PostMapping("/user/signup")
    public String signup(@Valid @RequestBody SignupRequestDto requestDto, BindingResult bindingResult) {
        // Validation 예외처리
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        if (fieldErrors.size() > 0) {
            for (FieldError fieldError : bindingResult.getFieldErrors()) {
                log.error(fieldError.getField() + " 필드 : " + fieldError.getDefaultMessage());
            }
            return "redirect:/api/user/signup-page";
        }

        userService.signup(requestDto);

        return "redirect:/api/user/login-page";
    }

}
