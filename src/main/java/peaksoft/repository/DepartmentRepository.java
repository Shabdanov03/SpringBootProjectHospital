package peaksoft.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import peaksoft.model.Department;

import java.util.List;

/**
 * Shabdanov Ilim
 **/

public interface DepartmentRepository extends JpaRepository<Department, Long> {

    @Query("from Department d join d.hospital h where h.id=:id")
    List<Department> getAllDepartments(Long id);
}
