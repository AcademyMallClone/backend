package com.korea.it.shopping.users.service;

import com.korea.it.shopping.users.entity.User;
import com.korea.it.shopping.users.repo.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // 회원가입 로직
    @Transactional
    public User registerUser(String username, String email, String password, String phone, String address, String role) {

        // 이메일 중복 체크 로직
        if (userRepository.existsByEmail(email)) {
            throw new IllegalStateException("이미 가입된 이메일입니다.");
        }

        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));  // 비밀번호 암호화
        user.setPhone(phone);
        user.setAddress(address);
        user.setRole(role);
        return userRepository.save(user);
    }

    // 로그인 인증 로직 (주석 처리)
    /*
    public UserEntity authenticateUser(String email, String password) {
        UserEntity userEntity = userRepository.findByEmail(email.toLowerCase())
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        if (!passwordEncoder.matches(password, userEntity.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        // 인증 정보 생성 및 SecurityContext에 설정
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(userEntity.getEmail(), password);
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        return userEntity;  // 로그인 성공 시 UserEntity 반환
    }
    */
}
