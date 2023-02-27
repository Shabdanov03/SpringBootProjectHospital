package peaksoft.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import peaksoft.exception.NotFoundException;
import peaksoft.model.Appointment;
import peaksoft.model.Department;
import peaksoft.model.Hospital;
import peaksoft.repository.AppointmentRepository;
import peaksoft.repository.DepartmentRepository;
import peaksoft.repository.HospitalRepository;
import peaksoft.service.DepartmentService;

import java.util.List;

/**
 * Shabdanov Ilim
 **/
@Service
@Transactional
@RequiredArgsConstructor

public class DepartmentServiceImpl implements DepartmentService {
    private final DepartmentRepository departmentRepository;
    private final HospitalRepository hospitalRepository;
    private final AppointmentRepository appointmentRepository;


    @Transactional
    @Override
    public void saveDepartment(Department department, Long hospitalId) {
        try {
            for (Department dep : departmentRepository.getAllDepartments(hospitalId)) {
                if (dep.getName().equals(department.getName())) {
                    throw new NotFoundException(" Such a department already exists ");
                }
            }
            department.setHospital(hospitalRepository.findById(hospitalId).get());
            departmentRepository.save(department);

        } catch (NotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public List<Department> getAllDepartments(Long id) {
        try {
            return departmentRepository.getAllDepartments(id);
        } catch (NotFoundException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Transactional
    @Override
    public void deleteDepartment(Long id) {
        try {
            Department department = departmentRepository.findById(id).get();
            for (int i = 0; i < department.getDoctors().size(); i++) {
                department.getDoctors().get(i).getDepartments().remove(department);
            }
            Hospital hospital = hospitalRepository.findById(department.getHospital().getId()).get();
            List<Appointment> appointments = hospital.getAppointments();
            for (Appointment appointment : appointments) {
                if (appointment.getDepartment().getId().equals(id)) {
                    appointmentRepository.deleteById(appointment.getId());
                }
            }
            hospital.getAppointments().removeAll(appointments);

            departmentRepository.deleteById(id);
        } catch (NotFoundException e) {
            System.out.println(e.getMessage());
        }


    }

    @Override
    public Department findByDepartmentId(Long id) {
        try {
            return departmentRepository.findById(id).get();
        } catch (NotFoundException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public void updateDepartment(Long id, Department department) {
        try {
            Department oldDepartment = departmentRepository.findById(id).get();
            oldDepartment.setName(department.getName());
        } catch (NotFoundException e) {
            System.out.println(e.getMessage());
        }
    }
}
