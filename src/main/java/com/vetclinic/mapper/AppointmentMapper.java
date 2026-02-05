package com.vetclinic.mapper;

import com.vetclinic.dto.AppointmentDTO;
import com.vetclinic.entity.Appointment;
import com.vetclinic.entity.Patient;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;


@Component
public class AppointmentMapper {

    public AppointmentDTO toDto(Appointment appointment) {
        if (appointment == null) {
            return null;
        }

        AppointmentDTO dto = new AppointmentDTO();
        dto.setId(appointment.getId());
        dto.setPatientId(appointment.getPatient() != null ? appointment.getPatient().getId() : null);
        dto.setDoctorName(appointment.getDoctorName());
        dto.setAppointmentDateTime(appointment.getAppointmentDateTime());
        dto.setReason(appointment.getReason());
        dto.setNotes(appointment.getNotes());
        dto.setStatus(appointment.getStatus());

        return dto;
    }

    public Appointment toEntity(AppointmentDTO dto) {
        if (dto == null) {
            return null;
        }

        Appointment appointment = new Appointment();
        appointment.setId(dto.getId());
        appointment.setDoctorName(dto.getDoctorName());
        appointment.setAppointmentDateTime(dto.getAppointmentDateTime());
        appointment.setReason(dto.getReason());
        appointment.setNotes(dto.getNotes());
        appointment.setStatus(dto.getStatus() != null ? dto.getStatus() : Appointment.AppointmentStatus.SCHEDULED);

        return appointment;
    }

    public Appointment toEntity(AppointmentDTO dto, Patient patient) {
        Appointment appointment = toEntity(dto);
        if (appointment != null && patient != null) {
            appointment.setPatient(patient);
        }
        return appointment;
    }

    public void updateEntityFromDto(AppointmentDTO dto, Appointment appointment) {
        if (dto == null || appointment == null) {
            return;
        }

        appointment.setDoctorName(dto.getDoctorName());
        appointment.setAppointmentDateTime(dto.getAppointmentDateTime());
        appointment.setReason(dto.getReason());
        appointment.setNotes(dto.getNotes());

        if (dto.getStatus() != null) {
            appointment.setStatus(dto.getStatus());
        }
    }

    public List<AppointmentDTO> toDtoList(List<Appointment> appointments) {
        if (appointments == null) {
            return null;
        }

        return appointments.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }
}