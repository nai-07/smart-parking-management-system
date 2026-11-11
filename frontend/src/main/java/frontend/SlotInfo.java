package frontend;

public class SlotInfo {

    private final int slotId;
    private final String slotNumber;
    private final String slotType;
    private final String status;
    private final int floorNumber;

    public SlotInfo(
            int slotId,
            String slotNumber,
            String slotType,
            String status,
            int floorNumber
    ) {
        this.slotId = slotId;
        this.slotNumber = slotNumber;
        this.slotType = slotType;
        this.status = status;
        this.floorNumber = floorNumber;
    }

    public int getSlotId() {
        return slotId;
    }

    public String getSlotNumber() {
        return slotNumber;
    }

    public String getSlotType() {
        return slotType;
    }

    public String getStatus() {
        return status;
    }

    public int getFloorNumber() {
        return floorNumber;
    }
}