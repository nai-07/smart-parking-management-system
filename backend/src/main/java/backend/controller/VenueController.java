package backend.controller;

import backend.model.Venue;
import backend.service.VenueService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/venues")
@CrossOrigin
public class VenueController {

    private final VenueService venueService;

    public VenueController(VenueService venueService) {
        this.venueService = venueService;
    }

    @GetMapping
    public List<Venue> getAllVenues() {
        return venueService.getAllVenues();
    }

    @GetMapping("/{venueId}")
    public Venue getVenueById(@PathVariable Integer venueId) {
        return venueService.getVenueById(venueId);
    }
}