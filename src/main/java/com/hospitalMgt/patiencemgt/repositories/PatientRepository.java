package com.hospitalMgt.patiencemgt.repositories;

import com.hospitalMgt.patiencemgt.entities.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PatientRepository extends JpaRepository<Patient, UUID> {
    Patient findByEmail(String email);
}
