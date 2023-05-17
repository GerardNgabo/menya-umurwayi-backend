package com.hospitalMgt.patiencemgt.controllers;

import com.hospitalMgt.patiencemgt.entities.Patient;
import com.hospitalMgt.patiencemgt.exceptions.NotFoundException;
import com.hospitalMgt.patiencemgt.repositories.PatientRepository;
import com.hospitalMgt.patiencemgt.services.PatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/patient")
@RequiredArgsConstructor
public class PatientController {

    private final PatientRepository patientRepository;

    private final PatientService patientService;


    @GetMapping
    public List<Patient> findAllPatients() {
        return patientService.getAllPatients();
    }

    @PostMapping
    public Patient createPatient(@RequestBody  Patient patient) {
        return patientService.createProfileDoc(patient);
    }

    @PutMapping(path = "{email}")
    public Patient updatePatient(@RequestBody Patient patient, @PathVariable("email") String email) throws NotFoundException {
        return patientService.updateProfile(email, patient);
    }

    @GetMapping(path = "{email}")
    public Patient getOnePatient(@PathVariable("email") String email) throws NotFoundException {
        return patientService.findOnePatient(email);
    }
}
