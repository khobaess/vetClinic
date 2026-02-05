package com.vetclinic.dto;

import com.vetclinic.entity.Appointment;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AppointmentDTO {
    
    private Long id;
    
    @NotNull(message = "ID пациента обязателен")
    private Long patientId;
    
    @NotBlank(message = "Имя врача обязательно")
    private String doctorName;
    
    @NotNull(message = "Дата и время записи обязательны")
    private LocalDateTime appointmentDateTime;
    
    private String reason;
    
    private String notes;
    
    private Appointment.AppointmentStatus status;
}
