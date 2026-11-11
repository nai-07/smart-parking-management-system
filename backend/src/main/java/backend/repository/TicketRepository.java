package backend.repository;

import backend.model.Ticket;
import backend.model.TicketStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TicketRepository extends JpaRepository<Ticket, Integer> {

    Optional<Ticket> findByVehicle_PlateNumberAndStatus(
            String plateNumber,
            TicketStatus status
    );

    Optional<Ticket> findByTicketIdAndStatus(
            Integer ticketId,
            TicketStatus status
    );
}