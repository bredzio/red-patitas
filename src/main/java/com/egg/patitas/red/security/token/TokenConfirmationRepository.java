package com.egg.patitas.red.security.token;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
@Transactional
public interface TokenConfirmationRepository extends JpaRepository<TokenConfirmation, Long> {
    Optional<TokenConfirmation> findByToken(String token);

    @Transactional
    @Modifying
    @Query("UPDATE TokenConfirmation c " + "SET c.confirmedAt = ?2 " + "WHERE c.token = ?1")
    int updateConfirmedAt(String token, LocalDateTime confirmedAt);

}
