package com.korea.it.shopping.carts.repo;

import com.korea.it.shopping.users.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<User, Long> {

}
