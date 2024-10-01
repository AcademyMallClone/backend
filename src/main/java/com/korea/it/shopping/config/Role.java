//권한 부여 설정

package com.korea.it.shopping.config;

import lombok.Getter;

@Getter
public enum Role {
    ROLE_ADMIN("관리자"), ROLE_USER("일반사용자");

    private String description;


    Role(String description) {
        this.description = description;
    }

}