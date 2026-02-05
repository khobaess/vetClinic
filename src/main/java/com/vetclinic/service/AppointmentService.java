package com.vetclinic.service;

import com.vetclinic.dto.AppointmentDTO;
import com.vetclinic.entity.Appointment;
import com.vetclinic.entity.Patient;
import com.vetclinic.exception.BusinessException;
import com.vetclinic.exception.ResourceNotFoundException;
import com.vetclinic.mapper.AppointmentMapper;
import com.vetclinic.repository.AppointmentRepository;
import com.vetclinic.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final PatientRepository patientRepository;
    private final AppointmentMapper appointmentMapper;

    @Autowired
    public AppointmentService(AppointmentRepository appointmentRepository,
                              PatientRepository patientRepository,
                              AppointmentMapper appointmentMapper) {
        this.appointmentRepository = appointmentRepository;
        this.patientRepository = patientRepository;
        this.appointmentMapper = appointmentMapper;
    }

    @Transactional(readOnly = true)
    public List<AppointmentDTO> getAllAppointments() {
        List<Appointment> appointments = appointmentRepository.findAll();

        return appointmentMapper.toDtoList(appointments);
    }

    @Transactional(readOnly = true)
    public AppointmentDTO getAppointmentById(Long id) {
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Запись", id));

        return appointmentMapper.toDto(appointment);
    }

    public AppointmentDTO createAppointment(AppointmentDTO appointmentDTO) {

        Patient patient = patientRepository.findById(appointmentDTO.getPatientId())
                .orElseThrow(() -> new ResourceNotFoundException("Пациент", appointmentDTO.getPatientId()));

        validateAppointmentTime(
                appointmentDTO.getAppointmentDateTime(),
                appointmentDTO.getDoctorName(),
                null
        );

        Appointment appointment = appointmentMapper.toEntity(appointmentDTO, patient);

        if (appointment.getStatus() == null) {
            appointment.setStatus(Appointment.AppointmentStatus.SCHEDULED);
        }

        Appointment savedAppointment = appointmentRepository.save(appointment);

        return appointmentMapper.toDto(savedAppointment);
    }

    public AppointmentDTO updateAppointment(Long id, AppointmentDTO appointmentDTO) {
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Запись", id));

        if (appointmentDTO.getPatientId() != null &&
                !appointmentDTO.getPatientId().equals(appointment.getPatient().getId())) {
            Patient patient = patientRepository.findById(appointmentDTO.getPatientId())
                    .orElseThrow(() -> new ResourceNotFoundException("Пациент", appointmentDTO.getPatientId()));
            appointment.setPatient(patient);
        }

        if (appointmentDTO.getAppointmentDateTime() != null) {
            validateAppointmentTime(
                    appointmentDTO.getAppointmentDateTime(),
                    appointmentDTO.getDoctorName(),
                    id
            );
        }

        appointmentMapper.updateEntityFromDto(appointmentDTO, appointment);

        Appointment updatedAppointment = appointmentRepository.save(appointment);

        return appointmentMapper.toDto(updatedAppointment);
    }

    public void deleteAppointment(Long id) {
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Запись", id));

        appointmentRepository.delete(appointment);
    }

    @Transactional(readOnly = true)
    public List<AppointmentDTO> getAppointmentsByPatientId(Long patientId) {
        List<Appointment> appointments = appointmentRepository.findByPatientId(patientId);

        return appointmentMapper.toDtoList(appointments);
    }

    @Transactional(readOnly = true)
    public List<AppointmentDTO> getAppointmentsByDoctor(String doctorName) {
        List<Appointment> appointments = appointmentRepository.findByDoctorName(doctorName);

        return appointmentMapper.toDtoList(appointments);
    }

    private void validateAppointmentTime(LocalDateTime appointmentDateTime,
                                         String doctorName,
                                         Long excludeAppointmentId) {
        if (appointmentDateTime == null || doctorName == null) {
            return;
        }

        LocalDateTime start = appointmentDateTime.minusMinutes(30);
        LocalDateTime end = appointmentDateTime.plusMinutes(30);

        List<Appointment> conflictingAppointments = appointmentRepository
                .findByAppointmentDateTimeBetween(start, end)
                .stream()
                .filter(apt -> apt.getDoctorName().equals(doctorName))
                .filter(apt -> excludeAppointmentId == null || !apt.getId().equals(excludeAppointmentId))
                .filter(apt -> apt.getStatus() != Appointment.AppointmentStatus.CANCELLED)
                .collect(Collectors.toList());

        if (!conflictingAppointments.isEmpty()) {
            throw new BusinessException(
                    String.format("Врач %s уже занят в это время. Выберите другое время.", doctorName));
        }
    }
}