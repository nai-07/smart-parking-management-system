package backend.repository;

import backend.model.Validation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ValidationRepository extends JpaRepository<Validation, Integer> {

    Optional<Validation> findByValidationCode(String validationCode);

    Optional<Validation> findByTicket_TicketId(Integer ticketId);
}