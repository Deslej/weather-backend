package com.codibly.weatherbackend.mapper;


import com.codibly.weatherbackend.constants.WeatherConstants;
import com.codibly.weatherbackend.dto.WeeklySummaryDto;
import com.fasterxml.jackson.databind.JsonNode;

public class WeeklySummaryDtoMapper {

    public static WeeklySummaryDto mapToWeeklySummaryDto(JsonNode daily) {
        double tempMin = getMin(daily.get("temperature_2m_min"));
        double tempMax = getMax(daily.get("temperature_2m_max"));
        double avgSunshine = getAverageSunshine(daily.get("sunshine_duration"));
        double avgPressure = getAveragePressure(daily.get("pressure_msl_mean"));
        int rainDays = countRainDays(daily.get("showers_sum"), daily.get("rain_sum"));

        String weatherSummary = rainDays > 3 ? "z opadami" : "bez opadów";

        return new WeeklySummaryDto(avgPressure, avgSunshine, tempMin, tempMax, weatherSummary);
    }

    private static double getMin(JsonNode array) {
        double min = array.get(0).asDouble();
        for (int i = 1; i < WeatherConstants.WEEK; i++) {
            min = Math.min(min, array.get(i).asDouble());
        }
        return min;
    }

    private static double getMax(JsonNode array) {
        double max = array.get(0).asDouble();
        for (int i = 1; i < WeatherConstants.WEEK; i++) {
            max = Math.max(max, array.get(i).asDouble());
        }
        return max;
    }

    private static double getAverageSunshine(JsonNode sunshineArray) {
        double total = 0;
        for (int i = 0; i < WeatherConstants.WEEK; i++) {
            total += sunshineArray.get(i).asDouble() / WeatherConstants.SECONDS_IN_HOUR;
        }
        return total / WeatherConstants.WEEK;
    }

    private static double getAveragePressure(JsonNode pressureArray) {
        double total = 0;
        for (int i = 0; i < WeatherConstants.WEEK; i++) {
            total += pressureArray.get(i).asDouble();
        }
        return total / WeatherConstants.WEEK;
    }

    private static int countRainDays(JsonNode showersArray, JsonNode rainArray) {
        int rainDays = 0;
        for (int i = 0; i < WeatherConstants.WEEK; i++) {
            if (showersArray.get(i).asDouble() > 0 || rainArray.get(i).asDouble() > 0) {
                rainDays++;
            }
        }
        return rainDays;
    }
}
