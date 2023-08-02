package com.sparta.hotel.service;

import com.sparta.hotel.dto.ApiResponseDto;
import com.sparta.hotel.dto.SignupRequestDto;
import com.sparta.hotel.dto.UsernameRequestDto;
import com.sparta.hotel.entity.User;
import com.sparta.hotel.entity.UserRoleEnum;
import com.sparta.hotel.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j(topic = "UserService")
@Service
@Component
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    private final String ADMIN_TOKEN = "AAABnvxRVklrnYxKZ0aHgTBcXukeZygoC";

    //회원가입 시 아이디 중복확인
    public ApiResponseDto checkUsername(UsernameRequestDto requestDto) {
        findUser(requestDto.getUsername()); // 동일 아이디가 있을 시, 중복된 사용자가 존재한다는 Exception 반환

        return new ApiResponseDto("사용 가능한 아이디입니다.");
    }

    @Transactional
    public ApiResponseDto signup(SignupRequestDto requestDto) {
        String username = requestDto.getUsername();
        String password = passwordEncoder.encode(requestDto.getPassword());
        String nickname = requestDto.getNickname();
        String email = requestDto.getEmail();
        // 아이디 중복 확인
        Optional<User> checkUsername = userRepository.findByUsername(username);
        // email 중복확인
        Optional<User> checkEmail = userRepository.findByEmail(email);
        // 회원가입 이메일 인증번호 검증여부 확인
//        Optional<SignupAuth> checkSignupAuth = signupAuthRepository.findByEmail(email);

        if (checkUsername.isPresent()) {
            throw new IllegalArgumentException("이미 존재하는 ID입니다.");
        }

        if (checkEmail.isPresent()) {
            throw new IllegalArgumentException("이미 존재하는 이메일입니다.");
        }
//        //이메일 인증 정보가 Table에 없거나, 상태코드가 1(OK)이 아닌경우
//        if (checkSignupAuth.isEmpty() || checkSignupAuth.get().getAuthStatus() != 1) {
//            throw new IllegalArgumentException("이메일 인증이 수행되지 않았습니다. 이메일 인증을 완료해주세요.");
//        }
        // 사용자 ROLE 확인
        UserRoleEnum role = UserRoleEnum.USER;
        if (requestDto.isAdmin()) {
            if (!ADMIN_TOKEN.equals(requestDto.getAdminToken())) {
                throw new IllegalArgumentException("관리자 암호가 틀려 등록이 불가능합니다.");
            }
            role = UserRoleEnum.ADMIN;
        }
        // 사용자 등록
        User user = new User(nickname, username, password, email, role);
        userRepository.save(user);
        // 비밀번호 관리 테이블에 등록
//        PasswordManager passwordManager = new PasswordManager(password, user);
//        passwordManagerRepository.save(passwordManager);
        return new ApiResponseDto("회원가입 완료");
    }

    public boolean findUser(String username) {
        Optional<User> checkUsername = userRepository.findByUsername(username);
        if (checkUsername.isPresent()) { //isPresent() --> 데이터에 동일 데이터가 있는지 확인.
            throw new IllegalArgumentException("중복된 사용자가 존재합니다.");
        }
        return true;
    }
}
