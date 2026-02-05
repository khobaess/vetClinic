package com.vetclinic.service;

import com.vetclinic.dto.PatientDTO;
import com.vetclinic.exception.ResourceNotFoundException;
import com.vetclinic.entity.Patient;
import com.vetclinic.repository.PatientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PatientServiceTest {
    
    @Mock
    private PatientRepository patientRepository;
    
    @InjectMocks
    private PatientService patientService;
    
    private Patient patient;
    private PatientDTO patientDTO;
    
    @BeforeEach
    void setUp() {
        patient = new Patient();
        patient.setId(1L);
        patient.setOwnerName("Иван Иванов");
        patient.setPetName("Барсик");
        patient.setSpecies("Кошка");
        patient.setBreed("Персидская");
        patient.setDateOfBirth(LocalDate.of(2020, 5, 15));
        
        patientDTO = new PatientDTO();
        patientDTO.setOwnerName("Иван Иванов");
        patientDTO.setPetName("Барсик");
        patientDTO.setSpecies("Кошка");
        patientDTO.setBreed("Персидская");
        patientDTO.setDateOfBirth(LocalDate.of(2020, 5, 15));
    }
    
    @Test
    void testGetPatientById_Success() {
        Long id = 1L;
        when(patientRepository.findById(id)).thenReturn(Optional.of(patient));

        PatientDTO result = patientService.getPatientById(id);

        assertNotNull(result);
        assertEquals(id, result.getId());
        verify(patientRepository, times(1)).findById(id);
    }
    
    @Test
    void testGetPatientById_NotFound() {
        Long id = 999L;
        when(patientRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            patientService.getPatientById(id);
        });
        verify(patientRepository, times(1)).findById(id);
    }
    
    @Test
    void testCreatePatient_Success() {
        when(patientRepository.save(any(Patient.class))).thenReturn(patient);

        PatientDTO result = patientService.createPatient(patientDTO);

        assertNotNull(result);
        verify(patientRepository, times(1)).save(any(Patient.class));
    }
    
    @Test
    void testUpdatePatient_Success() {
        Long id = 1L;
        PatientDTO updateDTO = new PatientDTO();
        updateDTO.setPetName("Мурзик");
        
        when(patientRepository.findById(id)).thenReturn(Optional.of(patient));
        when(patientRepository.save(any(Patient.class))).thenReturn(patient);

        PatientDTO result = patientService.updatePatient(id, updateDTO);

        assertNotNull(result);
        verify(patientRepository, times(1)).findById(id);
        verify(patientRepository, times(1)).save(patient);
    }
    
    @Test
    void testUpdatePatient_NotFound() {
        Long id = 999L;
        when(patientRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            patientService.updatePatient(id, patientDTO);
        });
        verify(patientRepository, times(1)).findById(id);
        verify(patientRepository, never()).save(any());
    }
    
    @Test
    void testDeletePatient_Success() {
        Long id = 1L;
        when(patientRepository.findById(id)).thenReturn(Optional.of(patient));
        doNothing().when(patientRepository).delete(patient);

        patientService.deletePatient(id);

        verify(patientRepository, times(1)).findById(id);
        verify(patientRepository, times(1)).delete(patient);
    }
    
    @Test
    void testDeletePatient_NotFound() {
        Long id = 999L;
        when(patientRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            patientService.deletePatient(id);
        });
        verify(patientRepository, times(1)).findById(id);
        verify(patientRepository, never()).delete((Patient) any());
    }
}
