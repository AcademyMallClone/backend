package com.korea.it.shopping.users.service;

import com.korea.it.shopping.users.entity.CustomUserDetails;
import com.korea.it.shopping.users.entity.UserEntity;
import com.korea.it.shopping.users.repo.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomUserDetailService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // 이메일로 사용자 검색
        UserEntity userEntity = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다: " + email));

        List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority(userEntity.getRole()));

        // CustomUserDetails 객체를 반환
        return new CustomUserDetails(userEntity, authorities);
    }
}
