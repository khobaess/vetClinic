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
@Table(name = "appointments")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Appointment {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotNull(message = "Пациент обязателен")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id", nullable = false)
    @JsonIgnoreProperties({"appointments", "medicalRecords"})
    private Patient patient;
    
    @NotBlank(message = "Имя врача обязательно")
    @Column(nullable = false)
    private String doctorName;
    
    @NotNull(message = "Дата и время записи обязательны")
    @Column(nullable = false)
    private LocalDateTime appointmentDateTime;
    
    @Column(length = 1000)
    private String reason;
    
    @Column(length = 1000)
    private String notes;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AppointmentStatus status = AppointmentStatus.SCHEDULED;
    
    public enum AppointmentStatus {
        SCHEDULED,
        COMPLETED,
        CANCELLED
    }
}
