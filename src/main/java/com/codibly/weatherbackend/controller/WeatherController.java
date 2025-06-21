package com.codibly.weatherbackend.controller;

import com.codibly.weatherbackend.dto.DayForecastDto;
import com.codibly.weatherbackend.dto.ErrorResponseDto;
import com.codibly.weatherbackend.dto.WeeklySummaryDto;
import com.codibly.weatherbackend.service.IWeatherService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Range;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(
        name = "Weather Forecast",
        description = "Endpoints for retrieving weather forecasts including daily detailed forecasts and weekly summaries based on geographical coordinates."
)
@RestController
@RequestMapping(path = "/api", produces = {MediaType.APPLICATION_JSON_VALUE})
@Validated
public class WeatherController {
    private final IWeatherService iWeatherService;

    public WeatherController(IWeatherService iWeatherService){
        this.iWeatherService = iWeatherService;
    }


    @Operation(
            summary = "Get 7-day weather forecast",
            description = "Retrieves a detailed 7-day daily weather forecast for the specified latitude and longitude coordinates. " +
                    "Each day's forecast includes the date, weather condition code, minimum and maximum temperatures (°C), " +
                    "and the estimated solar energy production in kWh. "
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "HTTP Status Bad Request",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "502",
                    description = "HTTP Status Bad Gateway",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    })
    @GetMapping("/dayForecast")
    public ResponseEntity<List<DayForecastDto>> getDailyForecast(@RequestParam
                                                                     @NotNull(message = "latitude must not be null")
                                                                     @Range(min = -90, max = 90, message = "latitude must be between -90 and 90") Double latitude,
                                                                 @RequestParam
                                                                    @NotNull(message = "longitude must not be null")
                                                                    @Range(min = -180,max = 180, message = "longitude must be between -180 and 180") Double longitude){

        List<DayForecastDto> dayilyForecast = iWeatherService.getDayilyForecast(latitude, longitude);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(dayilyForecast);
    }

    @Operation(
            summary = "Get weekly forecast summary",
            description = "Provides a weekly weather forecast summary for the specified latitude and longitude. " +
                    "The summary includes mean Sealevel pressure (hPa), mean sunshine duration (hours), " +
                    "minimum and maximum temperatures (°C), and a textual weather summary describing general conditions."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "HTTP Status Bad Request",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "502",
                    description = "HTTP Status Bad Gateway",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    })
    @GetMapping("/weeklyForecastSummary")
    public ResponseEntity<WeeklySummaryDto> getWeeklySummary(@RequestParam
                                                                 @NotNull(message = "latitude must not be null")
                                                                 @Range(min = -90, max = 90, message = "latitude must be between -90 and 90") Double latitude,
                                                             @RequestParam
                                                                 @NotNull(message = "longitude must not be null")
                                                                 @Range(min = -180,max = 180, message = "longitude must be between -180 and 180") Double longitude){

        WeeklySummaryDto weeklyForecastSummary = iWeatherService.getWeeklyForecastSummary(latitude, longitude);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(weeklyForecastSummary);
    }

}
