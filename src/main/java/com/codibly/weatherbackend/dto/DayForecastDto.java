package com.codibly.weatherbackend.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@Schema(
        name = "DayForecast",
        description = "Schema to hold DayForecast information"
)
public class DayForecastDto {
    @Schema(
            description = "Date of the forecasted day",
            example = "2025-06-18"
    )
    private LocalDate date;

    @Schema(
            description = "Weather condition code ",
            example = "1"
    )
    private int weatherCode;

    @Schema(
            description = "Minimum temperature during the day in Celsius",
            example = "12.5"
    )
    private double temperatureMin;

    @Schema(
            description = "Maximum temperature during the day in Celsius",
            example = "23.7"
    )
    private double temperatureMax;

    @Schema(
            description = "Estimated energy consumption in kWh for the day",
            example = "15.4"
    )
    private double estimatedEnergyKwh;
}
