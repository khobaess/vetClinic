package com.vetclinic.mapper;

import com.vetclinic.dto.MedicalRecordDTO;
import com.vetclinic.entity.MedicalRecord;
import com.vetclinic.entity.Patient;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class MedicalRecordMapper {

    public MedicalRecordDTO toDto(MedicalRecord medicalRecord) {
        if (medicalRecord == null) {
            return null;
        }

        MedicalRecordDTO dto = new MedicalRecordDTO();
        dto.setId(medicalRecord.getId());
        dto.setPatientId(medicalRecord.getPatient() != null ? medicalRecord.getPatient().getId() : null);
        dto.setRecordDate(medicalRecord.getRecordDate());
        dto.setDiagnosis(medicalRecord.getDiagnosis());
        dto.setSymptoms(medicalRecord.getSymptoms());
        dto.setTreatment(medicalRecord.getTreatment());
        dto.setPrescriptions(medicalRecord.getPrescriptions());
        dto.setNotes(medicalRecord.getNotes());
        dto.setDoctorName(medicalRecord.getDoctorName());

        return dto;
    }

    public MedicalRecord toEntity(MedicalRecordDTO dto) {
        if (dto == null) {
            return null;
        }

        MedicalRecord medicalRecord = new MedicalRecord();
        medicalRecord.setId(dto.getId());
        medicalRecord.setRecordDate(dto.getRecordDate());
        medicalRecord.setDiagnosis(dto.getDiagnosis());
        medicalRecord.setSymptoms(dto.getSymptoms());
        medicalRecord.setTreatment(dto.getTreatment());
        medicalRecord.setPrescriptions(dto.getPrescriptions());
        medicalRecord.setNotes(dto.getNotes());
        medicalRecord.setDoctorName(dto.getDoctorName());

        return medicalRecord;
    }

    public MedicalRecord toEntity(MedicalRecordDTO dto, Patient patient) {
        MedicalRecord medicalRecord = toEntity(dto);
        if (medicalRecord != null && patient != null) {
            medicalRecord.setPatient(patient);
        }
        return medicalRecord;
    }

    public void updateEntityFromDto(MedicalRecordDTO dto, MedicalRecord medicalRecord) {
        if (dto == null || medicalRecord == null) {
            return;
        }

        medicalRecord.setRecordDate(dto.getRecordDate());
        medicalRecord.setDiagnosis(dto.getDiagnosis());
        medicalRecord.setSymptoms(dto.getSymptoms());
        medicalRecord.setTreatment(dto.getTreatment());
        medicalRecord.setPrescriptions(dto.getPrescriptions());
        medicalRecord.setNotes(dto.getNotes());
        medicalRecord.setDoctorName(dto.getDoctorName());
    }

    public List<MedicalRecordDTO> toDtoList(List<MedicalRecord> medicalRecords) {
        if (medicalRecords == null) {
            return null;
        }

        return medicalRecords.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }
}