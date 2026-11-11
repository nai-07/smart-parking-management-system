package backend.controller;

import backend.model.Slot;
import backend.model.SlotType;
import backend.service.SlotService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/slots")
@CrossOrigin
public class SlotController {

    private final SlotService slotService;

    public SlotController(SlotService slotService) {
        this.slotService = slotService;
    }

    @GetMapping
    public List<Slot> getAllSlots() {
        return slotService.getAllSlots();
    }

    @GetMapping("/available/{slotType}")
    public List<Slot> getAvailableSlots(
            @PathVariable SlotType slotType) {

        return slotService.getAvailableSlots(slotType);
    }
}