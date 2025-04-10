package org.bagirov.twenty_second_hw.utill.Sensor;


import java.time.LocalDateTime;

public class SensorErrorResponse {
    private String message;
    private LocalDateTime timestamp;

    public SensorErrorResponse(String message, LocalDateTime timestamp) {
        this.message = message;
        this.timestamp = timestamp;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
