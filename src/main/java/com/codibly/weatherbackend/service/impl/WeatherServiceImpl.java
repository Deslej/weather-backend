package com.codibly.weatherbackend.service.impl;

import com.codibly.weatherbackend.dto.DayForecastDto;
import com.codibly.weatherbackend.dto.WeeklySummaryDto;
import com.codibly.weatherbackend.exception.ExternalApiClientException;
import com.codibly.weatherbackend.exception.ExternalApiConnectionException;
import com.codibly.weatherbackend.exception.ExternalApiServerException;
import com.codibly.weatherbackend.exception.InvalidResponseBodyException;
import com.codibly.weatherbackend.mapper.DayForecastDtoMapper;
import com.codibly.weatherbackend.mapper.WeeklySummaryDtoMapper;
import com.codibly.weatherbackend.service.IWeatherService;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Service
public class WeatherServiceImpl implements IWeatherService {

    private final RestTemplate restTemplate = new RestTemplate();

    /**
     * @param latitude  - Geographic latitude of the location (in degrees), must be between -90 and 90
     * @param longitude - Geographic longitude of the location (in degrees), must be between -180 and 180
     * @return List of DayForecastDto containing daily weather data including:
     * - date
     * - weather code
     * - minimum and maximum temperatures
     * - estimated solar energy production (in kWh)
     */
    @Override
    public List<DayForecastDto> getDayilyForecast(double latitude, double longitude) {
        String url = String.format(Locale.US,
                "https://api.open-meteo.com/v1/forecast?latitude=%f&longitude=%f&daily=temperature_2m_min,temperature_2m_max,weather_code,sunshine_duration&timezone=auto",
                latitude, longitude);

        JsonNode daily = extractDailyNodeFromApi(url);

        return DayForecastDtoMapper.mapToListDayForecastDto(daily);
    }

    /**
     * @param latitude  - Geographic latitude of the location (in degrees), must be between -90 and 90
     * @param longitude - Geographic longitude of the location (in degrees), must be between -180 and 180
     * @return WeeklySummaryDto containing summarized weekly weather data, including:
     * - mean pressure
     * - mean sunshine duration
     * - minimum and maximum temperature
     * - weather summary.
     */
    @Override
    public WeeklySummaryDto getWeeklyForecastSummary(double latitude, double longitude) {
        String url = String.format(Locale.US,
                "https://api.open-meteo.com/v1/forecast?latitude=%f&longitude=%f&daily=sunshine_duration,temperature_2m_min,temperature_2m_max,showers_sum,rain_sum,pressure_msl_mean&timezone=auto",
                latitude, longitude);

        JsonNode daily = extractDailyNodeFromApi(url);

        return WeeklySummaryDtoMapper.mapToWeeklySummaryDto(daily);
    }

    private JsonNode extractDailyNodeFromApi(String url) {
        try {
            ResponseEntity<JsonNode> response = restTemplate.getForEntity(url, JsonNode.class);

            JsonNode body = Optional.ofNullable(response.getBody())
                    .orElseThrow(() -> new InvalidResponseBodyException("Response body is null"));

            return Optional.ofNullable(body.get("daily"))
                    .orElseThrow(() -> new InvalidResponseBodyException("'daily' node is missing in API response"));
        }catch (HttpClientErrorException e) {
            String msg = String.format("Client error when calling external API: %s ", e.getStatusText());
            throw new ExternalApiClientException(msg,e.getStatusCode().value());
        } catch (HttpServerErrorException e) {
            String msg = String.format("Server error from external API: %s ", e.getStatusText());
            throw new ExternalApiServerException(msg,e.getStatusCode().value());
        }catch (ResourceAccessException e){
            String msg = "Failed to connect to external API: " + e.getMessage();
            throw new ExternalApiConnectionException(msg);
        }
    }
}
