package backend.service;

import backend.exception.DuplicateEntryException;
import backend.exception.InvalidPlateException;
import backend.model.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.regex.Pattern;

@Service
public class ParkingService {

    private final VehicleService vehicleService;
    private final VenueService venueService;
    private final SlotService slotService;
    private final TicketService ticketService;

    private static final Pattern INDIAN_PLATE_PATTERN =
            Pattern.compile("^[A-Z]{2}[0-9]{1,2}[A-Z]{1,3}[0-9]{4}$");

    public ParkingService(
            VehicleService vehicleService,
            VenueService venueService,
            SlotService slotService,
            TicketService ticketService) {

        this.vehicleService = vehicleService;
        this.venueService = venueService;
        this.slotService = slotService;
        this.ticketService = ticketService;
    }

    public Ticket enterVehicle(
            Integer venueId,
            String plateNumber,
            VehicleType vehicleType,
            String ownerName) {

        String normalizedPlate = plateNumber.toUpperCase().trim();

        validatePlate(normalizedPlate);

        if (ticketService.findActiveTicketByPlate(normalizedPlate).isPresent()) {
            throw new DuplicateEntryException(
                    "Vehicle is already parked in an active ticket."
            );
        }

        Venue venue = venueService.getVenueById(venueId);

        Vehicle vehicle = vehicleService
                .findByPlateNumber(normalizedPlate)
                .orElseGet(() -> {
                    Vehicle newVehicle = new Vehicle();
                    newVehicle.setPlateNumber(normalizedPlate);
                    newVehicle.setVehicleType(vehicleType);
                    newVehicle.setOwnerName(ownerName);
                    return vehicleService.saveVehicle(newVehicle);
                });

        SlotType slotType = convertVehicleTypeToSlotType(vehicleType);

        Slot slot = slotService.allocateSlot(slotType);
        slot.setStatus(SlotStatus.OCCUPIED);
        slotService.saveSlot(slot);

        Ticket ticket = new Ticket();
        ticket.setVehicle(vehicle);
        ticket.setVenue(venue);
        ticket.setSlot(slot);
        ticket.setEntryTime(LocalDateTime.now());
        ticket.setStatus(TicketStatus.ACTIVE);
        ticket.setValet(false);

        return ticketService.saveTicket(ticket);
    }

    private void validatePlate(String plateNumber) {
        if (!INDIAN_PLATE_PATTERN.matcher(plateNumber).matches()) {
            throw new InvalidPlateException(
                    "Invalid Indian vehicle plate number."
            );
        }
    }

    private SlotType convertVehicleTypeToSlotType(
            VehicleType vehicleType) {

        return switch (vehicleType) {
            case BIKE -> SlotType.BIKE;
            case CAR -> SlotType.CAR;
            case EV -> SlotType.EV;
        };
    }
}