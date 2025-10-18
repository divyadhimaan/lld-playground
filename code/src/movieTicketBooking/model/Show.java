package model;

import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

public class Show {
    private static final AtomicInteger id = new AtomicInteger(0);
    @Getter
    private final String showId;
    @Getter
    private final Date showDate;
    @Getter
    private final LocalDateTime startTime;
    @Getter
    private final LocalDateTime endTime;

    public Show(Date showDate, LocalDateTime startTime, LocalDateTime endTime){
        this.showId = String.valueOf(id.incrementAndGet());
        this.showDate =showDate;
        this.startTime=startTime;
        this.endTime=endTime;
    }

    public String getShowTime(){
        return this.startTime.toString();
    }
}
