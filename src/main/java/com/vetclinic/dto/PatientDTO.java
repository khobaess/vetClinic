package com.vetclinic.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PatientDTO {
    
    private Long id;
    
    @NotBlank(message = "Имя владельца обязательно")
    private String ownerName;
    
    @NotBlank(message = "Имя питомца обязательно")
    private String petName;
    
    @NotBlank(message = "Вид животного обязателен")
    private String species;
    
    @NotBlank(message = "Порода обязательна")
    private String breed;
    
    @NotNull(message = "Дата рождения обязательна")
    private LocalDate dateOfBirth;
    
    private String notes;
}
