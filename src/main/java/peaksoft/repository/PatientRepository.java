package peaksoft.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import peaksoft.model.Patient;

import java.util.List;

/**
 * Shabdanov Ilim
 **/

public interface PatientRepository extends JpaRepository<Patient, Long> {

    @Query("from  Patient p join p.hospital h where h.id = :id")
    List<Patient> getAllPatient(Long id);
}
