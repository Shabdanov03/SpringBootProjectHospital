package peaksoft.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import peaksoft.model.Hospital;

import java.util.List;

/**
 * Shabdanov Ilim
 **/

public interface HospitalRepository extends JpaRepository<Hospital, Long> {


    @Query("select h from Hospital h where h.name  ilike (:keyWord)")
    List<Hospital> search(String keyWord);


}
