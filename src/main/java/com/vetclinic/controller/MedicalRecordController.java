package com.vetclinic.controller;

import com.vetclinic.dto.MedicalRecordDTO;
import com.vetclinic.service.MedicalRecordService;
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
@RequestMapping("/api/medical-records")
@CrossOrigin(origins = "*")
@Tag(name = "Медицинские карточки", description = "API для управления медицинскими карточками")
public class MedicalRecordController {
    
    private final MedicalRecordService medicalRecordService;
    
    @Autowired
    public MedicalRecordController(MedicalRecordService medicalRecordService) {
        this.medicalRecordService = medicalRecordService;
    }
    
    @Operation(summary = "Получить все медицинские карточки")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Успешное получение списка карточек")
    })
    @GetMapping
    public ResponseEntity<List<MedicalRecordDTO>> getAllMedicalRecords() {
        List<MedicalRecordDTO> records = medicalRecordService.getAllMedicalRecords();
        return ResponseEntity.ok(records);
    }
    
    @Operation(summary = "Получить медицинскую карточку по ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Карточка найдена"),
        @ApiResponse(responseCode = "404", description = "Карточка не найдена")
    })
    @GetMapping("/{id}")
    public ResponseEntity<MedicalRecordDTO> getMedicalRecordById(
            @Parameter(description = "ID медицинской карточки") @PathVariable Long id) {
        MedicalRecordDTO record = medicalRecordService.getMedicalRecordById(id);
        return ResponseEntity.ok(record);
    }
    
    @Operation(summary = "Создать новую медицинскую карточку")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Карточка успешно создана"),
        @ApiResponse(responseCode = "400", description = "Ошибка валидации данных"),
        @ApiResponse(responseCode = "404", description = "Пациент не найден")
    })
    @PostMapping
    public ResponseEntity<MedicalRecordDTO> createMedicalRecord(@Valid @RequestBody MedicalRecordDTO medicalRecordDTO) {
        MedicalRecordDTO createdRecord = medicalRecordService.createMedicalRecord(medicalRecordDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdRecord);
    }
    
    @Operation(summary = "Обновить медицинскую карточку")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Карточка успешно обновлена"),
        @ApiResponse(responseCode = "404", description = "Карточка не найдена"),
        @ApiResponse(responseCode = "400", description = "Ошибка валидации данных")
    })
    @PutMapping("/{id}")
    public ResponseEntity<MedicalRecordDTO> updateMedicalRecord(
            @Parameter(description = "ID медицинской карточки") @PathVariable Long id,
            @Valid @RequestBody MedicalRecordDTO medicalRecordDTO) {
        MedicalRecordDTO updatedRecord = medicalRecordService.updateMedicalRecord(id, medicalRecordDTO);
        return ResponseEntity.ok(updatedRecord);
    }
    
    @Operation(summary = "Удалить медицинскую карточку")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Карточка успешно удалена"),
        @ApiResponse(responseCode = "404", description = "Карточка не найдена")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMedicalRecord(
            @Parameter(description = "ID медицинской карточки") @PathVariable Long id) {
        medicalRecordService.deleteMedicalRecord(id);
        return ResponseEntity.noContent().build();
    }
    
    @Operation(summary = "Получить медицинские карточки пациента")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Успешное получение карточек")
    })
    @GetMapping("/patient/{patientId}")
    public ResponseEntity<List<MedicalRecordDTO>> getMedicalRecordsByPatient(
            @Parameter(description = "ID пациента") @PathVariable Long patientId) {
        List<MedicalRecordDTO> records = medicalRecordService.getMedicalRecordsByPatientId(patientId);
        return ResponseEntity.ok(records);
    }
    
    @Operation(summary = "Получить медицинские карточки врача")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Успешное получение карточек")
    })
    @GetMapping("/doctor/{doctorName}")
    public ResponseEntity<List<MedicalRecordDTO>> getMedicalRecordsByDoctor(
            @Parameter(description = "Имя врача") @PathVariable String doctorName) {
        List<MedicalRecordDTO> records = medicalRecordService.getMedicalRecordsByDoctor(doctorName);
        return ResponseEntity.ok(records);
    }
}
