package com.sparta.hotel.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "users") // 생략 가능 - user
public class User extends TimeStamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //유저의 id 값
    @Column(nullable = false, unique = true)
    private String username;

    //유저의 비밀번호 값
    @Column(nullable = false)
    private String password;

    //유저의 이메일 값
    @Column(nullable = false, unique = true)
    private String email;

    //유저의 이름 또는 별명값
    @Column(nullable = false)
    private String nickname;

    //유저의 자기소개 값
    @Column
    private String oneLine;

    @Column
    private String image;

    private Long kakaoId;


    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private UserRoleEnum role;

    public User(String nickname,String username, String password, String email,UserRoleEnum role ) {
        this.nickname=nickname;
        this.username=username;
        this.password=password;
        this.email=email;
        this.role=role;

    }


}