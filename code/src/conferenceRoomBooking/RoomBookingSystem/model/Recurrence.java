package RoomBookingSystem.model;

import lombok.Getter;

import java.util.UUID;

@Getter
public class Recurrence {
    private final UUID recurrenceId;
    private final int numberOfWeeks;
    private final int startSlot;
    private final int durationInMinutes;
    private final int dayOfWeek;
    private final FrequencyType frequencyType; // Default frequency is every week

    public Recurrence(int numberOfWeeks, int startSlot, int durationInMinutes, int dayOfWeek, String frequency) {
        this.recurrenceId = UUID.randomUUID();
        this.numberOfWeeks = numberOfWeeks;
        this.startSlot = startSlot;
        this.durationInMinutes = durationInMinutes;
        this.dayOfWeek = dayOfWeek;
        this.frequencyType = FrequencyType.valueOf(frequency);
    }

}
