package com.vetclinic.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vetclinic.dto.PatientDTO;
import com.vetclinic.service.PatientService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PatientController.class)
class PatientControllerTest {
    
    @Autowired
    private MockMvc mockMvc;
    
    @MockBean
    private PatientService patientService;
    
    @Autowired
    private ObjectMapper objectMapper;
    
    @Test
    void testCreatePatient_Success() throws Exception {

        PatientDTO patientDTO = new PatientDTO();
        patientDTO.setOwnerName("Иван Иванов");
        patientDTO.setPetName("Барсик");
        patientDTO.setSpecies("Кошка");
        patientDTO.setBreed("Персидская");
        patientDTO.setDateOfBirth(LocalDate.of(2020, 5, 15));
        
        PatientDTO savedPatientDTO = new PatientDTO();
        savedPatientDTO.setId(1L);
        savedPatientDTO.setOwnerName("Иван Иванов");
        savedPatientDTO.setPetName("Барсик");
        
        when(patientService.createPatient(any(PatientDTO.class))).thenReturn(savedPatientDTO);

        mockMvc.perform(post("/api/patients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(patientDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.petName").value("Барсик"));
    }
    
    @Test
    void testCreatePatient_ValidationError() throws Exception {
        PatientDTO patientDTO = new PatientDTO();

        mockMvc.perform(post("/api/patients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(patientDTO)))
                .andExpect(status().isBadRequest());
    }
    
    @Test
    void testGetPatientById_Success() throws Exception {
        Long id = 1L;
        PatientDTO patientDTO = new PatientDTO();
        patientDTO.setId(id);
        patientDTO.setPetName("Барсик");
        
        when(patientService.getPatientById(id)).thenReturn(patientDTO);

        mockMvc.perform(get("/api/patients/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.petName").value("Барсик"));
    }
    
    @Test
    void testGetAllPatients_Success() throws Exception {
        List<PatientDTO> patients = new ArrayList<>();
        when(patientService.getAllPatients()).thenReturn(patients);

        mockMvc.perform(get("/api/patients"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }
}
