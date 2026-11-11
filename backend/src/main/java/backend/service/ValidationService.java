package backend.service;

import backend.model.Validation;
import backend.repository.ValidationRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ValidationService {

    private final ValidationRepository validationRepository;

    public ValidationService(ValidationRepository validationRepository) {
        this.validationRepository = validationRepository;
    }

    public Validation saveValidation(Validation validation) {
        return validationRepository.save(validation);
    }

    public Optional<Validation> findByCode(String validationCode) {
        return validationRepository.findByValidationCode(validationCode);
    }

    public Optional<Validation> findByTicketId(Integer ticketId) {
        return validationRepository.findByTicket_TicketId(ticketId);
    }
}