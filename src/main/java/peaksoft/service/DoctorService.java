package peaksoft.service;

import peaksoft.model.Doctor;

import java.util.List;

/**
 * Shabdanov Ilim
 **/
public interface DoctorService {
    void saveDoctor(Doctor doctor, Long hospitalId);

    List<Doctor> getAllDoctors(Long id);

    void deleteDoctor(Long id);

    Doctor findByDoctorId(Long id);

    void updateDoctor(Long id, Doctor doctor);

}
