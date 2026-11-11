package backend.repository;

import backend.model.Slot;
import backend.model.SlotStatus;
import backend.model.SlotType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SlotRepository extends JpaRepository<Slot, Integer> {

    List<Slot> findByStatus(SlotStatus status);

    List<Slot> findBySlotTypeAndStatus(SlotType slotType, SlotStatus status);

    Optional<Slot> findFirstBySlotTypeAndStatusOrderBySlotIdAsc(
            SlotType slotType,
            SlotStatus status
    );
}