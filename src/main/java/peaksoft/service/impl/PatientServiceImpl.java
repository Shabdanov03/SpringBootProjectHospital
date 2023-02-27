package peaksoft.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import peaksoft.exception.NotFoundException;
import peaksoft.model.Appointment;
import peaksoft.model.Hospital;
import peaksoft.model.Patient;
import peaksoft.repository.AppointmentRepository;
import peaksoft.repository.HospitalRepository;
import peaksoft.repository.PatientRepository;
import peaksoft.service.PatientService;

import java.util.List;

/**
 * Shabdanov Ilim
 **/
@Service
@Transactional
@RequiredArgsConstructor

public class PatientServiceImpl implements PatientService {
    private final PatientRepository patientRepository;
    private final HospitalRepository hospitalRepository;
    private final AppointmentRepository appointmentRepository;

    @Transactional
    @Override
    public void savePatient(Patient patient, Long hospitalId) {
        try {
            patient.setHospital(hospitalRepository.findById(hospitalId).get());
            patientRepository.save(patient);
        } catch (NotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public List<Patient> getAllPatients(Long id) {
        try {
            return patientRepository.getAllPatient(id);
        }catch (NotFoundException e){
            System.out.println(e.getMessage());
        }return null;
    }

    @Transactional
    @Override
    public void deletePatient(Long id) {
        try {
            Patient patient = patientRepository.findById(id).get();

            List<Patient> patients = patient.getHospital().getPatients();
            patients.removeIf(x->x.getId().equals(id));

            Hospital hospital = hospitalRepository.findById(patient.getHospital().getId()).get();
            List<Appointment> appointments = hospital.getAppointments();
            for (Appointment appointment : appointments) {
                if (appointment.getPatient().getId().equals(id)) {
                    appointmentRepository.deleteById(appointment.getId());
                }
            }
            hospital.getAppointments().removeAll(appointments);

            patientRepository.deleteById(id);
        } catch (NotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public Patient findByPatientId(Long id) {
        try {
            return patientRepository.findById(id).get();
        }catch (NotFoundException e){
            System.out.println(e.getMessage());
        }return null;
    }

    @Override
    public void updatePatient(Long id, Patient patient) {
        try {
            Patient oldPatient = patientRepository.findById(id).get();
            oldPatient.setFirstName(patient.getFirstName());
            oldPatient.setLastName(patient.getLastName());
            oldPatient.setPhoneNumber(patient.getPhoneNumber());
            oldPatient.setGender(patient.getGender());
            oldPatient.setEmail(patient.getEmail());
        } catch (NotFoundException e) {
            System.out.println(e.getMessage());
        }
    }
}
