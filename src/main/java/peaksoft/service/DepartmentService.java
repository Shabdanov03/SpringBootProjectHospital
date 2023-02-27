package peaksoft.service;

import peaksoft.model.Department;

import java.util.List;

/**
 * Shabdanov Ilim
 **/
public interface DepartmentService {
    void saveDepartment(Department department, Long hospitalId);

    List<Department> getAllDepartments(Long id);

    void deleteDepartment(Long id);

    Department findByDepartmentId(Long id);

    void updateDepartment(Long id, Department department);
}
