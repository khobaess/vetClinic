package com.vetclinic.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MedicalRecordDTO {
    
    private Long id;
    
    @NotNull(message = "ID пациента обязателен")
    private Long patientId;
    
    @NotNull(message = "Дата записи обязательна")
    private LocalDateTime recordDate;
    
    @NotBlank(message = "Диагноз обязателен")
    private String diagnosis;
    
    private String symptoms;
    
    private String treatment;
    
    private String prescriptions;
    
    private String notes;
    
    @NotBlank(message = "Имя врача обязательно")
    private String doctorName;
}
