package com.vetclinic.service;

import com.vetclinic.dto.MedicalRecordDTO;
import com.vetclinic.entity.MedicalRecord;
import com.vetclinic.entity.Patient;
import com.vetclinic.exception.ResourceNotFoundException;
import com.vetclinic.mapper.MedicalRecordMapper;
import com.vetclinic.repository.MedicalRecordRepository;
import com.vetclinic.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class MedicalRecordService {

    private final MedicalRecordRepository medicalRecordRepository;
    private final PatientRepository patientRepository;
    private final MedicalRecordMapper medicalRecordMapper;

    @Autowired
    public MedicalRecordService(MedicalRecordRepository medicalRecordRepository,
                                PatientRepository patientRepository,
                                MedicalRecordMapper medicalRecordMapper) {
        this.medicalRecordRepository = medicalRecordRepository;
        this.patientRepository = patientRepository;
        this.medicalRecordMapper = medicalRecordMapper;
    }

    @Transactional(readOnly = true)
    public List<MedicalRecordDTO> getAllMedicalRecords() {
        List<MedicalRecord> records = medicalRecordRepository.findAll();

        return medicalRecordMapper.toDtoList(records);
    }

    @Transactional(readOnly = true)
    public MedicalRecordDTO getMedicalRecordById(Long id) {
        MedicalRecord medicalRecord = medicalRecordRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Медицинская карточка", id));

        return medicalRecordMapper.toDto(medicalRecord);
    }

    public MedicalRecordDTO createMedicalRecord(MedicalRecordDTO medicalRecordDTO) {
        Patient patient = patientRepository.findById(medicalRecordDTO.getPatientId())
                .orElseThrow(() -> new ResourceNotFoundException("Пациент", medicalRecordDTO.getPatientId()));

        MedicalRecord medicalRecord = medicalRecordMapper.toEntity(medicalRecordDTO, patient);

        MedicalRecord savedRecord = medicalRecordRepository.save(medicalRecord);

        return medicalRecordMapper.toDto(savedRecord);
    }

    public MedicalRecordDTO updateMedicalRecord(Long id, MedicalRecordDTO medicalRecordDTO) {
        MedicalRecord medicalRecord = medicalRecordRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Медицинская карточка", id));

        if (medicalRecordDTO.getPatientId() != null &&
                !medicalRecordDTO.getPatientId().equals(medicalRecord.getPatient().getId())) {
            Patient patient = patientRepository.findById(medicalRecordDTO.getPatientId())
                    .orElseThrow(() -> new ResourceNotFoundException("Пациент", medicalRecordDTO.getPatientId()));
            medicalRecord.setPatient(patient);
        }

        medicalRecordMapper.updateEntityFromDto(medicalRecordDTO, medicalRecord);

        MedicalRecord updatedRecord = medicalRecordRepository.save(medicalRecord);

        return medicalRecordMapper.toDto(updatedRecord);
    }

    public void deleteMedicalRecord(Long id) {
        MedicalRecord medicalRecord = medicalRecordRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Медицинская карточка", id));

        medicalRecordRepository.delete(medicalRecord);
    }

    @Transactional(readOnly = true)
    public List<MedicalRecordDTO> getMedicalRecordsByPatientId(Long patientId) {
        List<MedicalRecord> records = medicalRecordRepository.findByPatientIdOrderByRecordDateDesc(patientId);

        return medicalRecordMapper.toDtoList(records);
    }

    @Transactional(readOnly = true)
    public List<MedicalRecordDTO> getMedicalRecordsByDoctor(String doctorName) {
        List<MedicalRecord> records = medicalRecordRepository.findByDoctorName(doctorName);

        return medicalRecordMapper.toDtoList(records);
    }
}