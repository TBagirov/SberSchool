package org.bagirov.twenty_second_hw.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

@Schema(
        description = "включает в себя наименование сенсора"
)
public class SensorDto {
    @NotEmpty
    @Size(min=3, max=30, message = "The sensor name can contain from 3 to 30 characters")
    @Schema(description = "Наименование сенсора", example = "Sensor1")
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public SensorDto() {}

    public SensorDto(String name) {
        this.name = name;
    }

}
