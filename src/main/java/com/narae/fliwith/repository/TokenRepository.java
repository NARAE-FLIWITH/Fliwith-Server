package com.narae.fliwith.repository;

import com.narae.fliwith.domain.Token;
import com.narae.fliwith.domain.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TokenRepository extends JpaRepository<Token, String> {
    Optional<Token> findByUser(User user);

}
