package com.hospitalMgt.patiencemgt.repositories;

import com.hospitalMgt.patiencemgt.entities.Otp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OtpRepository extends JpaRepository<Otp, Long> {
    Otp findByToken(String token);
}
