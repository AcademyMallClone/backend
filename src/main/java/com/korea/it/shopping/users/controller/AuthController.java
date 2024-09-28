package com.korea.it.shopping.users.controller;

import com.korea.it.shopping.users.entity.UserEntity;
import com.korea.it.shopping.users.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    // 회원가입 요청 처리
    @PostMapping("/join")
    public ResponseEntity<String> registerUser(@RequestBody UserEntity userEntity) {
        // 회원가입 로직 실행
        userService.registerUser(
                userEntity.getUsername(),
                userEntity.getEmail(),
                userEntity.getPassword(),
                userEntity.getPhone(),   // phone 값 추가
                userEntity.getAddress(),
                "ROLE_USER"
        );
        return ResponseEntity.ok("회원가입 성공");
    }

    // 로그인 요청 처리
    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestBody Map<String, String> loginRequest) {
        String email = loginRequest.get("email");
        String password = loginRequest.get("password");

        try {
            UserEntity user = userService.authenticateUser(email, password);
            return ResponseEntity.ok("로그인 성공");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("로그인 실패: " + e.getMessage());
        }
    }

    @GetMapping("/profile")
    public ResponseEntity<Map<String, Object>> getProfile() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();

            // principal이 UserEntity인 경우 처리
            if (principal instanceof UserEntity) {
                System.out.println("sdfsjfdlkk");
                User user = (User) principal;
                Map<String, Object> profileData = new HashMap<>();
                profileData.put("username", user.getUsername());
            //  profileData.put("roles", userEntity.getRole());  // 역할 정보 가져오기 -> 아직 로직 구현하지 않음
                return ResponseEntity.ok(profileData);
            }
        }

        return ResponseEntity.status(401).body(null);  // 인증되지 않은 경우 401 반환
    }

}
