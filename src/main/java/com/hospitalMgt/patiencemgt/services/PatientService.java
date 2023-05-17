package com.hospitalMgt.patiencemgt.services;

import com.hospitalMgt.patiencemgt.entities.Patient;
import com.hospitalMgt.patiencemgt.exceptions.NotFoundException;
import com.hospitalMgt.patiencemgt.repositories.PatientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PatientService {
    private final PatientRepository patientRepository;


    public Patient createProfileDoc(Patient patient) {
        return patientRepository.save(patient);
    }

    public List<Patient> getAllPatients() {
        return patientRepository.findAll();
    }

    public Patient findOnePatient(String email) throws NotFoundException {
        Patient patient = patientRepository.findByEmail(email);
        if(patient == null) throw new NotFoundException("Patient not found");
        return patient;
    }

    public Patient updateProfile(String email, Patient patient) throws NotFoundException {
        Patient findPatient = patientRepository.findByEmail(email);
        if(findPatient == null) throw new NotFoundException("Patient not found");
        if(patient.getFirstName() != null) {
            findPatient.setFirstName(patient.getFirstName());
        }
        if(patient.getLastName() != null) {
            findPatient.setLastName(patient.getLastName());
        }
        if(patient.getGender() != null) {
            findPatient.setGender(patient.getGender());
        }
        if(patient.getAddress() != null) {
            findPatient.setAddress(patient.getAddress());
        }
        if(patient.getContactNumber() != null) {
            findPatient.setContactNumber(patient.getContactNumber());
        }
        if(patient.getInsuranceProvider() != null) {
            findPatient.setInsuranceProvider(patient.getInsuranceProvider());
        }
        if(patient.getMedicalHistory() != null) {
            findPatient.setMedicalHistory(patient.getMedicalHistory());
        }
        if(patient.getReasonOfVisit() != null) {
            findPatient.setReasonOfVisit(patient.getReasonOfVisit());
        }
        if(patient.getSymptoms() != null) {
            findPatient.setSymptoms(patient.getSymptoms());
        }
        return patientRepository.save(findPatient);
    }
}
