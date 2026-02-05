package com.vetclinic.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "patients")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Patient {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "Имя владельца обязательно")
    @Column(nullable = false)
    private String ownerName;
    
    @NotBlank(message = "Имя питомца обязательно")
    @Column(nullable = false)
    private String petName;
    
    @NotBlank(message = "Вид животного обязателен")
    @Column(nullable = false)
    private String species;
    
    @NotBlank(message = "Порода обязательна")
    @Column(nullable = false)
    private String breed;
    
    @NotNull(message = "Дата рождения обязательна")
    @Column(nullable = false)
    private LocalDate dateOfBirth;
    
    @Column(length = 500)
    private String notes;
    
    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties("patient")
    private List<Appointment> appointments = new ArrayList<>();
    
    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties("patient")
    private List<MedicalRecord> medicalRecords = new ArrayList<>();
}
