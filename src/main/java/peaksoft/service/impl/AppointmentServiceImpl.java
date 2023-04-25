package peaksoft.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import peaksoft.exception.NotFoundException;
import peaksoft.model.*;
import peaksoft.repository.*;
import peaksoft.service.AppointmentService;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Shabdanov Ilim
 **/
@Transactional
@Service
@RequiredArgsConstructor
public class AppointmentServiceImpl implements AppointmentService {
    private final AppointmentRepository appointmentRepository;
    private final PatientRepository patientRepository;
    private final DepartmentRepository departmentRepository;
    private final DoctorRepository doctorRepository;
    private final HospitalRepository hospitalRepository;

    @Transactional
    @Override
    public void saveAppointment(Appointment appointment, Long hospitalId) {
        try {
            Appointment oldAppointment = new Appointment();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate date = LocalDate.parse(appointment.getInputDate(), formatter);
            oldAppointment.setDate(date);

            if (date.isBefore(LocalDate.now())) {
                throw new RuntimeException();
            }
            oldAppointment.setPatient(patientRepository.findById(appointment.getPatientId()).orElseThrow(IllegalArgumentException::new));
            oldAppointment.setDepartment(departmentRepository.findById(appointment.getDepartmentId()).orElseThrow(IllegalArgumentException::new));
            oldAppointment.setDoctor(doctorRepository.findById(appointment.getDoctorId()).orElseThrow(IllegalArgumentException::new));
            hospitalRepository.findById(hospitalId).orElseThrow(IllegalArgumentException::new).getAppointments().add(oldAppointment);

            appointmentRepository.save(oldAppointment);
        } catch (RuntimeException e) {
            throw new RuntimeException();
        }
    }

    @Override
    public List<Appointment> getAllAppointments(Long id) {
        try {
            return appointmentRepository.getAllAppointments(id);
        } catch (NotFoundException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Transactional
    @Override
    public void deleteAppointment(Long id, Long hospitalId) {
        try {
            Hospital hospital = hospitalRepository.findById(hospitalId).orElseThrow(IllegalArgumentException::new);
            if (hospital.getAppointments() != null) {
                for (int i = 0; i < hospital.getAppointments().size(); i++) {
                    if (hospital.getAppointments().get(i).getId().equals(id)) {
                        hospital.getAppointments().remove(hospital.getAppointments().get(i));
                    }
                }
            }
            appointmentRepository.deleteById(id);
        } catch (NotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public Appointment findByAppointmentId(Long id) {
        try {
            return appointmentRepository.findById(id).orElseThrow(IllegalArgumentException::new);
        } catch (NotFoundException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public void updateAppointment(Long id, Appointment appointment) {
        try {
            Appointment oldAppointment = appointmentRepository.findById(id).orElseThrow(IllegalArgumentException::new);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate date = LocalDate.parse(appointment.getInputDate(), formatter);
            oldAppointment.setDate(date);
            if (date.isBefore(LocalDate.now())) {
                throw new RuntimeException();
            }
            oldAppointment.setPatient(patientRepository.findById(appointment.getPatientId()).orElseThrow(IllegalArgumentException::new));
            oldAppointment.setDepartment(departmentRepository.findById(appointment.getDepartmentId()).orElseThrow(IllegalArgumentException::new));
            oldAppointment.setDoctor(doctorRepository.findById(appointment.getDoctorId()).orElseThrow(IllegalArgumentException::new));
        } catch (RuntimeException e) {
            throw new RuntimeException();
        }
    }
}
