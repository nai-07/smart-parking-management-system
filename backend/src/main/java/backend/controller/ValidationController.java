package backend.controller;

import backend.model.Validation;
import backend.service.ValidationService;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/validations")
@CrossOrigin
public class ValidationController {

    private final ValidationService validationService;

    public ValidationController(ValidationService validationService) {
        this.validationService = validationService;
    }

    @GetMapping("/code/{validationCode}")
    public Optional<Validation> getByCode(
            @PathVariable String validationCode) {

        return validationService.findByCode(validationCode);
    }

    @GetMapping("/ticket/{ticketId}")
    public Optional<Validation> getByTicketId(
            @PathVariable Integer ticketId) {

        return validationService.findByTicketId(ticketId);
    }

    @PostMapping
    public Validation createValidation(
            @RequestBody Validation validation) {

        return validationService.saveValidation(validation);
    }
}