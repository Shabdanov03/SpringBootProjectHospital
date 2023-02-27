package peaksoft.service;

import peaksoft.model.Appointment;

import java.util.List;

/**
 * Shabdanov Ilim
 **/
public interface AppointmentService {
    void saveAppointment(Appointment appointment,Long hospitalId);

    List<Appointment> getAllAppointments(Long id);

    void deleteAppointment(Long id,Long hospitalId);

    Appointment findByAppointmentId(Long id);

    void updateAppointment(Long id, Appointment appointment);
}
