package peaksoft.service;

import peaksoft.model.Hospital;

import java.util.List;

/**
 * Shabdanov Ilim
 **/
public interface HospitalService {
    void saveHospital(Hospital hospital);

    List<Hospital> getAllHospitals();
    List<Hospital> getAllHospitals(String keyWord);

    void deleteHospital(Long id);

    Hospital findByHospitalId(Long id);

    void updateHospital(Long id, Hospital hospital);
}
