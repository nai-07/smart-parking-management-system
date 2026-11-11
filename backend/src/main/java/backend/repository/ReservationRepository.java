package backend.repository;

import backend.model.Reservation;
import backend.model.ReservationStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Integer> {

    List<Reservation> findByStatus(ReservationStatus status);

    List<Reservation> findByVehicleNumberAndStatus(
            String vehicleNumber,
            ReservationStatus status
    );
}