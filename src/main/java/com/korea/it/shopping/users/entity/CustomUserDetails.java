package com.korea.it.shopping.users.entity;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class CustomUserDetails implements UserDetails {

    private final UserEntity userEntity;
    private final List<GrantedAuthority> authorities;

    public CustomUserDetails(UserEntity userEntity, List<GrantedAuthority> authorities) {
        this.userEntity = userEntity;
        this.authorities = authorities;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;  // 사용자의 권한 목록
    }

    @Override
    public String getPassword() {
        return userEntity.getPassword();  // 비밀번호 반환
    }

    @Override
    public String getUsername() {
        return userEntity.getUsername();  // 사용자이름 반환
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;  // 계정 만료 여부 (true로 기본 설정)
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;  // 계정 잠금 여부
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;  // 자격 증명 만료 여부
    }

    @Override
    public boolean isEnabled() {
        return true;  // 계정 활성화 여부
    }

    // UserEntity에서 커스텀 활용
    public String getEmail() {
        return userEntity.getEmail();
    }

    public String getPhone() {
        return userEntity.getPhone();
    }

    public String getAddress() {
        return userEntity.getAddress();
    }
}
