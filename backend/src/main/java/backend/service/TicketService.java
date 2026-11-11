package backend.service;

import backend.model.Ticket;
import backend.model.TicketStatus;
import backend.repository.TicketRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TicketService {

    private final TicketRepository ticketRepository;

    public TicketService(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    public Optional<Ticket> findActiveTicketByPlate(String plateNumber) {
        return ticketRepository.findByVehicle_PlateNumberAndStatus(
                plateNumber,
                TicketStatus.ACTIVE
        );
    }

    public Optional<Ticket> findActiveTicketById(Integer ticketId) {
        return ticketRepository.findByTicketIdAndStatus(
                ticketId,
                TicketStatus.ACTIVE
        );
    }

    public Ticket saveTicket(Ticket ticket) {
        return ticketRepository.save(ticket);
    }
}