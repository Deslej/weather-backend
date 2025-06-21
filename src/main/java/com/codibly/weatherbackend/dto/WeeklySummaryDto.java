package com.codibly.weatherbackend.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Schema(
        name = "WeeklySummary",
        description = "Schema to hold summary of the weather for a week"
)
public class WeeklySummaryDto {

    @Schema(
            description = "Average atmospheric pressure over the week in hPa",
            example = "1013.5"
    )
    private double meanPressure;

    @Schema(
            description = "Average daily sunshine duration over the week in hours",
            example = "6.7"
    )
    private double meanSunshineDuration;

    @Schema(
            description = "Minimum temperature recorded during the week in Celsius",
            example = "10.3"
    )
    private double temperatureMin;

    @Schema(
            description = "Maximum temperature recorded during the week in Celsius",
            example = "25.8"
    )
    private double temperatureMax;

    @Schema(
            description = "Summary description of the general weather conditions for the week",
            example = "Rainy week"
    )
    private String weatherSummary;

}
