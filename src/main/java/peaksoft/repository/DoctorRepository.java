package peaksoft.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import peaksoft.model.Doctor;

import java.util.List;

/**
 * Shabdanov Ilim
 **/

public interface DoctorRepository extends JpaRepository<Doctor,Long> {

    @Query("from Doctor d join d.hospital h where h.id=:id")
    List<Doctor> getAllDoctors(Long id);
}
