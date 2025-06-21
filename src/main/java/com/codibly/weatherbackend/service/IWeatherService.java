package com.codibly.weatherbackend.service;

import com.codibly.weatherbackend.dto.DayForecastDto;
import com.codibly.weatherbackend.dto.WeeklySummaryDto;

import java.util.List;

public interface IWeatherService {

    /**
     *
     * @param latitude - Geographic latitude of the location (in degrees), must be between -90 and 90
     * @param longitude - Geographic longitude of the location (in degrees), must be between -180 and 180
     * @return List of DayForecastDto containing daily weather data including:
     *         - date
     *         - weather code
     *         - minimum and maximum temperatures
     *         - estimated solar energy production (in kWh)
     */
    List<DayForecastDto> getDayilyForecast (double latitude, double longitude);

    /**
     *
     * @param latitude - Geographic latitude of the location (in degrees), must be between -90 and 90
     * @param longitude - Geographic longitude of the location (in degrees), must be between -180 and 180
     * @return WeeklySummaryDto containing summarized weekly weather data, including:
     *           mean pressure, mean sunshine duration, minimum and maximum temperature,
     *           and a general weather summary.
     */
    WeeklySummaryDto getWeeklyForecastSummary (double latitude, double longitude);

}
