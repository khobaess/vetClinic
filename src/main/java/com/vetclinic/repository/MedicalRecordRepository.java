package com.vetclinic.repository;

import com.vetclinic.entity.MedicalRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MedicalRecordRepository extends JpaRepository<MedicalRecord, Long> {
    
    List<MedicalRecord> findByDoctorName(String doctorName);
    
    List<MedicalRecord> findByPatientIdOrderByRecordDateDesc(Long patientId);
}
