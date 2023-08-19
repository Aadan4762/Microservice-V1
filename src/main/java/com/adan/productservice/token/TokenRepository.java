package com.adan.productservice.token;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token, Integer> {
    List<Token> findAllValidTokenByUser(Integer id);
    Optional<Token> findByToken(String token);
}
