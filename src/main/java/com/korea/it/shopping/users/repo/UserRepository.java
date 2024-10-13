package com.korea.it.shopping.users.repo;

import com.korea.it.shopping.users.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
//    Optional<UserEntity> findByUsername(String username);
    Optional<User> findByUserId(Long userid);
    //이메일이 존재하는지 확인
    boolean existsByEmail(String email);
}
