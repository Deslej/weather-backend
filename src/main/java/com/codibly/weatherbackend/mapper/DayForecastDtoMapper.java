package com.codibly.weatherbackend.mapper;

import com.codibly.weatherbackend.constants.WeatherConstants;
import com.codibly.weatherbackend.dto.DayForecastDto;
import com.fasterxml.jackson.databind.JsonNode;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DayForecastDtoMapper {

    public static List<DayForecastDto> mapToListDayForecastDto(JsonNode daily){
        List<DayForecastDto> dayForecastDtoList = new ArrayList<>();

        for (int i = 0; i < WeatherConstants.WEEK; i++) {
            String dateString = daily.get("time").get(i).asText();
            LocalDate date = LocalDate.parse(dateString);
            int weatherCode = daily.get("weather_code").get(i).asInt();
            double tempMin = daily.get("temperature_2m_min").get(i).asDouble();
            double tempMax = daily.get("temperature_2m_max").get(i).asDouble();
            double sunshineDuration = daily.get("sunshine_duration").get(i).asDouble() / WeatherConstants.SECONDS_IN_HOUR;
            double estimatedEnergyKwh = WeatherConstants.PANEL_POWER * sunshineDuration * WeatherConstants.PANEL_EFFICIENCY;

            dayForecastDtoList.add(new DayForecastDto(date,weatherCode,tempMin,tempMax,estimatedEnergyKwh));
        }
        return dayForecastDtoList;
    }
}
