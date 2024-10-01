package com.korea.it.shopping.users.service;

import com.korea.it.shopping.users.entity.UserEntity;
import com.korea.it.shopping.users.repo.UserRepository;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.servlet.server.Session;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // 회원가입 로직
    @Transactional
    public UserEntity registerUser(String username, String email, String password, String phone, String address, String role) {
        UserEntity user = new UserEntity();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));  // 비밀번호 암호화
        user.setPhone(phone);
        user.setAddress(address);
        user.setRole(role); //아직 관리자 일반 유저를 나누지 않음
        return userRepository.save(user);
    }

//    // 로그인 인증 로직 이전 버전
//    public UserEntity authenticateUser(String email, String password) {
//        UserEntity userEntity = userRepository.findByEmail(email.toLowerCase())
//                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));
//
//        if (!passwordEncoder.matches(password, userEntity.getPassword())) {
//            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
//        }
//
//        // 인증 정보 생성 및 SecurityContext에 설정
//        UsernamePasswordAuthenticationToken authenticationToken =
//                new UsernamePasswordAuthenticationToken(userEntity.getEmail(), password);
//        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
//
//        return userEntity;  // 로그인 성공 시 UserEntity 반환
//    }


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // 이메일로 사용자 검색
        return (UserDetails) userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));
    }

}
