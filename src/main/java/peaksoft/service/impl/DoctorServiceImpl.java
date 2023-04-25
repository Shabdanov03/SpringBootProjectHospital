package peaksoft.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import peaksoft.exception.NotFoundException;
import peaksoft.model.Appointment;
import peaksoft.model.Department;
import peaksoft.model.Doctor;
import peaksoft.model.Hospital;
import peaksoft.repository.AppointmentRepository;
import peaksoft.repository.DepartmentRepository;
import peaksoft.repository.DoctorRepository;
import peaksoft.repository.HospitalRepository;
import peaksoft.service.DoctorService;

import java.util.List;

/**
 * Shabdanov Ilim
 **/
@Service
@Transactional
@RequiredArgsConstructor

public class DoctorServiceImpl implements DoctorService {
    private final DoctorRepository doctorRepository;
    private final HospitalRepository hospitalRepository;
    private final DepartmentRepository departmentRepository;
    private final AppointmentRepository appointmentRepository;


    @Transactional
    @Override
    public void saveDoctor(Doctor doctor, Long hospitalId) {
        try {
            doctor.setHospital(hospitalRepository.findById(hospitalId).orElseThrow(IllegalArgumentException::new));
            doctorRepository.save(doctor);
        } catch (NotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public List<Doctor> getAllDoctors(Long id) {
        try {
            return doctorRepository.getAllDoctors(id);
        } catch (NotFoundException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Transactional
    @Override
    public void deleteDoctor(Long id) {
        try {
            Doctor doctor = doctorRepository.findById(id).orElseThrow(IllegalArgumentException::new);

            List<Doctor> doctors = doctor.getHospital().getDoctors();
            doctors.removeIf(x -> x.getId().equals(id));

            Hospital hospital = hospitalRepository.findById(doctor.getHospital().getId()).get();
            List<Appointment> appointments = hospital.getAppointments();
            for (Appointment appointment : appointments) {
                if (appointment.getDoctor().getId().equals(id)) {
                    appointmentRepository.deleteById(appointment.getId());
                }
            }
            hospital.getAppointments().removeAll(appointments);
            doctorRepository.deleteById(id);
        } catch (NotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public Doctor findByDoctorId(Long id) {
        try {
            return doctorRepository.findById(id).orElseThrow(IllegalArgumentException::new);
        } catch (NotFoundException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public void updateDoctor(Long id, Doctor doctor) {
        try {
            Doctor oldDoctor = doctorRepository.findById(id).orElseThrow(IllegalArgumentException::new);
            oldDoctor.setFirstName(doctor.getLastName());
            oldDoctor.setLastName(doctor.getLastName());
            oldDoctor.setPosition(doctor.getPosition());
            oldDoctor.setEmail(doctor.getEmail());
        } catch (NotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void assign(Long id, List<Long> departmentId) {
        Doctor doctor = doctorRepository.findById(id).orElseThrow(() -> new NotFoundException("Not found !"));
        List<Department> departments = departmentRepository.findAllById(departmentId);
        for (Department department : departments) {
            doctor.addDepartment(department);
            department.addDoctor(doctor);
        }
        doctorRepository.save(doctor);
    }
}
