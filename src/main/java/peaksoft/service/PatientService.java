package peaksoft.service;

import peaksoft.model.Patient;

import java.util.List;

/**
 * Shabdanov Ilim
 **/
public interface PatientService {

    void savePatient(Patient patient, Long hospitalId);

    List<Patient> getAllPatients(Long id);

    void deletePatient(Long id);

    Patient findByPatientId(Long id);

    void updatePatient(Long id,Patient patient);
}
