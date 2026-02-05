package com.vetclinic.mapper;

import com.vetclinic.dto.PatientDTO;
import com.vetclinic.entity.Patient;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;


@Component
public class PatientMapper {

    public PatientDTO toDto(Patient patient) {
        if (patient == null) {
            return null;
        }

        PatientDTO dto = new PatientDTO();
        dto.setId(patient.getId());
        dto.setOwnerName(patient.getOwnerName());
        dto.setPetName(patient.getPetName());
        dto.setSpecies(patient.getSpecies());
        dto.setBreed(patient.getBreed());
        dto.setDateOfBirth(patient.getDateOfBirth());
        dto.setNotes(patient.getNotes());

        return dto;
    }

    public Patient toEntity(PatientDTO dto) {
        if (dto == null) {
            return null;
        }

        Patient patient = new Patient();
        patient.setId(dto.getId());
        patient.setOwnerName(dto.getOwnerName());
        patient.setPetName(dto.getPetName());
        patient.setSpecies(dto.getSpecies());
        patient.setBreed(dto.getBreed());
        patient.setDateOfBirth(dto.getDateOfBirth());
        patient.setNotes(dto.getNotes());

        return patient;
    }

    public void updateEntityFromDto(PatientDTO dto, Patient patient) {
        if (dto == null || patient == null) {
            return;
        }

        patient.setOwnerName(dto.getOwnerName());
        patient.setPetName(dto.getPetName());
        patient.setSpecies(dto.getSpecies());
        patient.setBreed(dto.getBreed());
        patient.setDateOfBirth(dto.getDateOfBirth());
        patient.setNotes(dto.getNotes());
    }

    public List<PatientDTO> toDtoList(List<Patient> patients) {
        if (patients == null) {
            return null;
        }

        return patients.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

}