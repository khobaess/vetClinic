package com.vetclinic.repository;

import com.vetclinic.entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    
    List<Appointment> findByPatientId(Long patientId);
    
    List<Appointment> findByDoctorName(String doctorName);
    
    List<Appointment> findByAppointmentDateTimeBetween(LocalDateTime start, LocalDateTime end);

}
