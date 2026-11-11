package backend.service;

import backend.model.Slot;
import backend.model.SlotStatus;
import backend.model.SlotType;
import backend.repository.SlotRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SlotService {

    private final SlotRepository slotRepository;

    public SlotService(SlotRepository slotRepository) {
        this.slotRepository = slotRepository;
    }

    public List<Slot> getAllSlots() {
        return slotRepository.findAll();
    }

    public Slot saveSlot(Slot slot) {
        return slotRepository.save(slot);
    }

    public synchronized Slot findFreeSlot(
            SlotType slotType) {

        return slotRepository.findAll()
                .stream()
                .filter(slot ->
                        slot.getSlotType() == slotType &&
                        slot.getStatus() == SlotStatus.FREE
                )
                .findFirst()
                .orElse(null);
    }
}