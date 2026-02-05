package com.vetclinic.controller;

import com.vetclinic.dto.PatientDTO;
import com.vetclinic.service.PatientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/patients")
@CrossOrigin(origins = "*")
@Tag(name = "Пациенты", description = "API для управления пациентами")
public class PatientController {
    
    private final PatientService patientService;
    
    @Autowired
    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }
    
    @Operation(summary = "Получить всех пациентов", 
               description = "Возвращает список всех пациентов")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Успешное получение списка пациентов")
    })
    @GetMapping
    public ResponseEntity<List<PatientDTO>> getAllPatients() {
        List<PatientDTO> patients = patientService.getAllPatients();
        return ResponseEntity.ok(patients);
    }
    
    @Operation(summary = "Получить пациента по ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Пациент найден"),
        @ApiResponse(responseCode = "404", description = "Пациент не найден")
    })
    @GetMapping("/{id}")
    public ResponseEntity<PatientDTO> getPatientById(
            @Parameter(description = "ID пациента") @PathVariable Long id) {
        PatientDTO patient = patientService.getPatientById(id);
        return ResponseEntity.ok(patient);
    }
    
    @Operation(summary = "Создать нового пациента")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Пациент успешно создан"),
        @ApiResponse(responseCode = "400", description = "Ошибка валидации данных")
    })
    @PostMapping
    public ResponseEntity<PatientDTO> createPatient(@Valid @RequestBody PatientDTO patientDTO) {
        PatientDTO createdPatient = patientService.createPatient(patientDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdPatient);
    }
    
    @Operation(summary = "Обновить пациента")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Пациент успешно обновлен"),
        @ApiResponse(responseCode = "404", description = "Пациент не найден"),
        @ApiResponse(responseCode = "400", description = "Ошибка валидации данных")
    })
    @PutMapping("/{id}")
    public ResponseEntity<PatientDTO> updatePatient(
            @Parameter(description = "ID пациента") @PathVariable Long id,
            @Valid @RequestBody PatientDTO patientDTO) {
        PatientDTO updatedPatient = patientService.updatePatient(id, patientDTO);
        return ResponseEntity.ok(updatedPatient);
    }
    
    @Operation(summary = "Удалить пациента")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Пациент успешно удален"),
        @ApiResponse(responseCode = "404", description = "Пациент не найден")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePatient(
            @Parameter(description = "ID пациента") @PathVariable Long id) {
        patientService.deletePatient(id);
        return ResponseEntity.noContent().build();
    }
    
    @Operation(summary = "Поиск пациентов", 
               description = "Поиск пациентов по различным критериям")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Успешный поиск")
    })
    @GetMapping("/search")
    public ResponseEntity<List<PatientDTO>> searchPatients(
            @Parameter(description = "Имя питомца") @RequestParam(required = false) String petName,
            @Parameter(description = "Имя владельца") @RequestParam(required = false) String ownerName,
            @Parameter(description = "Вид животного") @RequestParam(required = false) String species) {
        List<PatientDTO> patients = patientService.searchPatients(petName, ownerName, species);
        return ResponseEntity.ok(patients);
    }
}
