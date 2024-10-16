package com.korea.it.shopping.users.controller;

import com.korea.it.shopping.users.entity.CustomUserDetails;
import com.korea.it.shopping.users.entity.User;
import com.korea.it.shopping.users.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;

    // 생성자를 통해 AuthenticationManager 주입
    public AuthController(UserService userService, AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;  // 주입된 AuthenticationManager 사용
    }

    // 회원가입 요청 처리
    @PostMapping("/join")
    public ResponseEntity<String> registerUser(@RequestBody User user) {
        try {
            // 회원가입 로직 수행
            userService.registerUser(
                    user.getUsername(),
                    user.getEmail(),
                    user.getPassword(),
                    user.getPhone(),
                    user.getAddress(),
                    user.getRole() != null ? user.getRole() : "ROLE_USER"
            );
            return ResponseEntity.ok("회원가입 성공");
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("회원가입 실패: 이미 가입된 이메일입니다.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("회원가입 실패: " + e.getMessage());
        }
    }


    // 로그인 요청 처리
    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestBody Map<String, String> loginRequest) {
        String email = loginRequest.get("email");
        String password = loginRequest.get("password");

        try {
            // AuthenticationManager를 사용하여 인증 처리
            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(email, password);
            Authentication authentication = authenticationManager.authenticate(authToken);

            // 인증 성공 시 SecurityContextHolder에 저장
            SecurityContextHolder.getContext().setAuthentication(authentication);

            // 세션 정보 로그 출력
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            if (auth != null && auth.isAuthenticated()) {
                System.out.println("Current Authenticated User: " + auth.getName());
            }

            return ResponseEntity.ok("로그인 성공");
        } catch (Exception e) {
            // 인증 실패 시 에러 메시지 반환
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인 실패: " + e.getMessage());
        }
    }

    // 프로필 조회 요청 처리
    @GetMapping("/profile")
    public ResponseEntity<Map<String, Object>> getProfile(Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) {
            CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();

            // 프로필 정보 생성
            Map<String, Object> profileData = new HashMap<>();
            profileData.put("username", customUserDetails.getUsername());
            profileData.put("email", customUserDetails.getEmail());

            return ResponseEntity.ok(profileData);
        }
        // 인증되지 않은 경우 401 반환
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logoutUser(HttpServletRequest request, HttpServletResponse response) {
        SecurityContextHolder.clearContext(); // SecurityContextHolder 비우기
        request.getSession().invalidate(); // 세션 무효화
        return ResponseEntity.ok("로그아웃 성공");
    }
}
