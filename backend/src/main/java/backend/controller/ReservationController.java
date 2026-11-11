package backend.controller;

import backend.model.Reservation;
import backend.service.ReservationService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reservations")
@CrossOrigin
public class ReservationController {

    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping("/active")
    public List<Reservation> getActiveReservations() {
        return reservationService.getActiveReservations();
    }

    @GetMapping("/vehicle/{vehicleNumber}")
    public List<Reservation> getReservationsByVehicle(
            @PathVariable String vehicleNumber) {

        return reservationService.getActiveReservationsByVehicle(
                vehicleNumber
        );
    }

    @PostMapping
    public Reservation createReservation(
            @RequestBody Reservation reservation) {

        return reservationService.saveReservation(reservation);
    }
}