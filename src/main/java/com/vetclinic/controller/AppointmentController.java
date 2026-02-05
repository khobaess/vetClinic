package com.vetclinic.controller;

import com.vetclinic.dto.AppointmentDTO;
import com.vetclinic.service.AppointmentService;
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
@RequestMapping("/api/appointments")
@CrossOrigin(origins = "*")
@Tag(name = "Записи к врачу", description = "API для управления записями на прием")
public class AppointmentController {
    
    private final AppointmentService appointmentService;
    
    @Autowired
    public AppointmentController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }
    
    @Operation(summary = "Получить все записи")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Успешное получение списка записей")
    })
    @GetMapping
    public ResponseEntity<List<AppointmentDTO>> getAllAppointments() {
        List<AppointmentDTO> appointments = appointmentService.getAllAppointments();
        return ResponseEntity.ok(appointments);
    }
    
    @Operation(summary = "Получить запись по ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Запись найдена"),
        @ApiResponse(responseCode = "404", description = "Запись не найдена")
    })
    @GetMapping("/{id}")
    public ResponseEntity<AppointmentDTO> getAppointmentById(
            @Parameter(description = "ID записи") @PathVariable Long id) {
        AppointmentDTO appointment = appointmentService.getAppointmentById(id);
        return ResponseEntity.ok(appointment);
    }
    
    @Operation(summary = "Создать новую запись")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Запись успешно создана"),
        @ApiResponse(responseCode = "400", description = "Ошибка валидации или бизнес-логики"),
        @ApiResponse(responseCode = "404", description = "Пациент не найден")
    })
    @PostMapping
    public ResponseEntity<AppointmentDTO> createAppointment(@Valid @RequestBody AppointmentDTO appointmentDTO) {
        AppointmentDTO createdAppointment = appointmentService.createAppointment(appointmentDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdAppointment);
    }
    
    @Operation(summary = "Обновить запись")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Запись успешно обновлена"),
        @ApiResponse(responseCode = "404", description = "Запись не найдена"),
        @ApiResponse(responseCode = "400", description = "Ошибка валидации или бизнес-логики")
    })
    @PutMapping("/{id}")
    public ResponseEntity<AppointmentDTO> updateAppointment(
            @Parameter(description = "ID записи") @PathVariable Long id,
            @Valid @RequestBody AppointmentDTO appointmentDTO) {
        AppointmentDTO updatedAppointment = appointmentService.updateAppointment(id, appointmentDTO);
        return ResponseEntity.ok(updatedAppointment);
    }
    
    @Operation(summary = "Удалить запись")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Запись успешно удалена"),
        @ApiResponse(responseCode = "404", description = "Запись не найдена")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAppointment(
            @Parameter(description = "ID записи") @PathVariable Long id) {
        appointmentService.deleteAppointment(id);
        return ResponseEntity.noContent().build();
    }
    
    @Operation(summary = "Получить записи пациента")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Успешное получение записей")
    })
    @GetMapping("/patient/{patientId}")
    public ResponseEntity<List<AppointmentDTO>> getAppointmentsByPatient(
            @Parameter(description = "ID пациента") @PathVariable Long patientId) {
        List<AppointmentDTO> appointments = appointmentService.getAppointmentsByPatientId(patientId);
        return ResponseEntity.ok(appointments);
    }
    
    @Operation(summary = "Получить записи врача")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Успешное получение записей")
    })
    @GetMapping("/doctor/{doctorName}")
    public ResponseEntity<List<AppointmentDTO>> getAppointmentsByDoctor(
            @Parameter(description = "Имя врача") @PathVariable String doctorName) {
        List<AppointmentDTO> appointments = appointmentService.getAppointmentsByDoctor(doctorName);
        return ResponseEntity.ok(appointments);
    }
}
