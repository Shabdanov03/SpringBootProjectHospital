package peaksoft.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import peaksoft.model.Appointment;

import java.util.List;

/**
 * Shabdanov Ilim
 **/
public interface AppointmentRepository extends JpaRepository<Appointment,Long> {

    @Query("select a from Hospital h join h.appointments a where h.id=:id  order by a.id ")
    List<Appointment> getAllAppointments(Long id);
}
