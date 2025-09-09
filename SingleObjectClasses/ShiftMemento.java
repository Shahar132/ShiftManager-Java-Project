package SingleObjectClasses;

import java.time.LocalDateTime;

public class ShiftMemento {
    private final LocalDateTime startDateTime;
    private final LocalDateTime endDateTime;

    public ShiftMemento(LocalDateTime startDateTime, LocalDateTime endDateTime) {
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
    }

    public LocalDateTime getStartDateTime() {
        return startDateTime;
    }

    public LocalDateTime getEndDateTime() {
        return endDateTime;
    }
}
