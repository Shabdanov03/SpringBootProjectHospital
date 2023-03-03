package peaksoft.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import peaksoft.exception.NotFoundException;
import peaksoft.model.Hospital;
import peaksoft.repository.HospitalRepository;
import peaksoft.service.HospitalService;

import java.util.List;

/**
 * Shabdanov Ilim
 **/
@Transactional
@Service
@RequiredArgsConstructor

public class HospitalServiceImpl implements HospitalService {

    private final HospitalRepository repository;

    @Override
    public void saveHospital(Hospital hospital) {
        try {
            repository.save(hospital);
        } catch (NotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public List<Hospital> getAllHospitals() {
        try {
            return repository.findAll();
        } catch (NotFoundException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public List<Hospital> getAllHospitals(String keyWord) {
        try {
            if (keyWord != null && !keyWord.trim().isEmpty()) {
                return repository.search("%" + keyWord + "%");
            } else {
                return repository.findAll();
            }
        } catch (NotFoundException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public void deleteHospital(Long id) {
        try {
            repository.deleteById(id);
        } catch (NotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public Hospital findByHospitalId(Long id) {
        try {
            return repository.findById(id).get();
        } catch (NotFoundException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public void updateHospital(Long id, Hospital hospital) {
        try {
            Hospital oldHospital = repository.findById(id).get();
            oldHospital.setName(hospital.getName());
            oldHospital.setAddress(hospital.getAddress());
            oldHospital.setImage(hospital.getImage());
        } catch (NotFoundException e) {
            System.out.println(e.getMessage());
        }
    }
}

