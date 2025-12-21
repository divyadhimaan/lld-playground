package parkingLot.model;

import lombok.Getter;

@Getter
public class Level {
    private final int levelNumber;
    private final int totalSlots;
    private final int availableSlots;

    public Level(int levelNumber, int totalSlots) {
        this.levelNumber = levelNumber;
        this.totalSlots = totalSlots;
        this.availableSlots = totalSlots;
    }
}
