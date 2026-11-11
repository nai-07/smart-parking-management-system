package backend.controller;

import backend.service.EntryExitService;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/parking")
@CrossOrigin
public class ParkingController {

    private final EntryExitService entryExitService;

    public ParkingController(
            EntryExitService entryExitService) {

        this.entryExitService = entryExitService;
    }

    @PostMapping("/exit/ticket/{ticketId}")
    public BigDecimal exitByTicket(
            @PathVariable Integer ticketId,
            @RequestParam(required = false)
            String validationCode) {

        return entryExitService.processExitByTicketId(
                ticketId,
                validationCode
        );
    }

    @PostMapping("/exit/plate/{plateNumber}")
    public BigDecimal exitByPlate(
            @PathVariable String plateNumber) {

        return entryExitService.processExitByPlate(
                plateNumber
        );
    }
}