package backend.service;

import backend.model.*;
import backend.repository.ReservationRepository;
import backend.repository.SlotRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final SlotRepository slotRepository;

    public ReservationService(
            ReservationRepository reservationRepository,
            SlotRepository slotRepository) {

        this.reservationRepository = reservationRepository;
        this.slotRepository = slotRepository;
    }

    public Reservation saveReservation(
            Reservation reservation) {

        Slot slot = slotRepository.findById(
                reservation.getSlot().getSlotId()
        ).orElseThrow(() ->
                new RuntimeException("Slot not found."));

        if (slot.getStatus() != SlotStatus.FREE) {
            throw new RuntimeException(
                    "Selected slot is not available."
            );
        }

        slot.setStatus(SlotStatus.RESERVED);
        slotRepository.save(slot);

        reservation.setSlot(slot);

        return reservationRepository.save(reservation);
    }

    public List<Reservation> getActiveReservations() {
        return reservationRepository.findByStatus(
                ReservationStatus.ACTIVE
        );
    }

    public List<Reservation> getActiveReservationsByVehicle(
            String vehicleNumber) {

        return reservationRepository
                .findByVehicleNumberAndStatus(
                        vehicleNumber,
                        ReservationStatus.ACTIVE
                );
    }
}