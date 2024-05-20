package com.narae.fliwith.repository;

import com.narae.fliwith.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmail(String email);
    boolean existsByNickname(String nickname);
    Optional<User> findById(Long id);

    Optional<User> findByEmail(String email);
    Optional<User> findByAuth(String uuid);
}
