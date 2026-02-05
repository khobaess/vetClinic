package com.vetclinic.service;

import com.vetclinic.dto.PatientDTO;
import com.vetclinic.mapper.PatientMapper;
import com.vetclinic.exception.ResourceNotFoundException;
import com.vetclinic.entity.Patient;
import com.vetclinic.repository.PatientRepository;
import com.vetclinic.repository.specification.PatientSpecifications;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class PatientService {

    private final PatientRepository patientRepository;
    private final PatientMapper patientMapper;

    @Autowired
    public PatientService(PatientRepository patientRepository, PatientMapper patientMapper) {
        this.patientRepository = patientRepository;
        this.patientMapper = patientMapper;
    }

    @Transactional(readOnly = true)
    public List<PatientDTO> getAllPatients() {
        List<Patient> patients = patientRepository.findAll();
        return patientMapper.toDtoList(patients);
    }

    @Transactional(readOnly = true)
    public PatientDTO getPatientById(Long id) {
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Пациент", id));

        return patientMapper.toDto(patient);
    }

    public PatientDTO createPatient(PatientDTO patientDTO) {
        Patient patient = patientMapper.toEntity(patientDTO);
        Patient savedPatient = patientRepository.save(patient);
        return patientMapper.toDto(savedPatient);
    }

    public PatientDTO updatePatient(Long id, PatientDTO patientDTO) {
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Пациент", id));

        patientMapper.updateEntityFromDto(patientDTO, patient);

        Patient updatedPatient = patientRepository.save(patient);

        return patientMapper.toDto(updatedPatient);
    }

    public void deletePatient(Long id) {
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Пациент", id));

        patientRepository.delete(patient);
    }

    @Transactional(readOnly = true)
    public List<PatientDTO> searchPatients(String petName, String ownerName, String species) {
        Specification<Patient> spec = PatientSpecifications.searchPatients(petName, ownerName, species);
        List<Patient> patients = patientRepository.findAll(spec);

        return patientMapper.toDtoList(patients);
    }
}
