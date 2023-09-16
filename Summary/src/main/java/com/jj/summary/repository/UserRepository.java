package com.jj.summary.repository;

import com.jj.summary.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByEmail(String email);
    Optional<User> findByNickName(String nickName);
    User findById(int id);
//    User findByEmail(String email);
}
