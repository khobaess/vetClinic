package com.vetclinic.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "medical_records")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MedicalRecord {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotNull(message = "Пациент обязателен")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id", nullable = false)
    @JsonIgnoreProperties({"appointments", "medicalRecords"})
    private Patient patient;
    
    @NotNull(message = "Дата записи обязательна")
    @Column(nullable = false)
    private LocalDateTime recordDate;
    
    @NotBlank(message = "Диагноз обязателен")
    @Column(nullable = false, length = 500)
    private String diagnosis;
    
    @Column(length = 2000)
    private String symptoms;
    
    @Column(length = 2000)
    private String treatment;
    
    @Column(length = 2000)
    private String prescriptions;
    
    @Column(length = 1000)
    private String notes;
    
    @NotBlank(message = "Имя врача обязательно")
    @Column(nullable = false)
    private String doctorName;
}
