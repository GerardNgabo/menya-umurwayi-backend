package com.hospitalMgt.patiencemgt.repositories;

import com.hospitalMgt.patiencemgt.entities.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface TokenRepository extends JpaRepository<Token, Long> {
    @Query("""
            select t from Token t inner join User u on t.user.id = u.id
             where u.id = :userId and (t.expired = false or t.revoked = false)
            """)
    List<Token> findAllUserTokens(UUID userId);

    Optional<Token> findByToken(String token);
}