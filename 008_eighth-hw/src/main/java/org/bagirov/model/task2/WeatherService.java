package org.bagirov.model.task2;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

public class WeatherService {

    private static final String API_KEY = "6047afd3ff6b4bc282a140025243012";
    private static final String WEATHER_API_URL = "https://api.weatherapi.com/v1/current.json";

    public static String getWeatherData(String cityName) throws IOException {
        OkHttpClient httpClient = new OkHttpClient();

        String requestUrl = String.format("%s?key=%s&q=%s&aqi=no", WEATHER_API_URL, API_KEY, cityName);

        Request httpRequest = new Request.Builder()
                .url(requestUrl)
                .build();

        try (Response httpResponse = httpClient.newCall(httpRequest).execute()) {
            if (!httpResponse.isSuccessful()) {
                throw new IOException("Unexpected response code: " + httpResponse);
            }
            return httpResponse.body().string();
        }
    }

    public static WeatherInfo parseWeatherJson(String json) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(json, WeatherInfo.class);
    }

    public static void printWeatherInfo(WeatherInfo weatherInfo) {
        String city = weatherInfo.getLocation().getName();
        double temperature = weatherInfo.getCurrent().getTempC();
        String weatherCondition = weatherInfo.getCurrent().getCondition().getText();

        System.out.printf("\tПогода в городе %s\n" +
                "  Температура: %.1f°C\n" +
                "  Состояние: %s%n",
                city,
                temperature,
                weatherCondition);
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class WeatherInfo {
        private LocationDetails location;
        private CurrentWeather current;

        public LocationDetails getLocation() {
            return location;
        }

        public void setLocation(LocationDetails location) {
            this.location = location;
        }

        public CurrentWeather getCurrent() {
            return current;
        }

        public void setCurrent(CurrentWeather current) {
            this.current = current;
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class LocationDetails {
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class CurrentWeather {
        @JsonProperty("temp_c")
        private double tempCelsius;

        private WeatherCondition condition;

        public double getTempC() {
            return tempCelsius;
        }

        public void setTempC(double tempC) {
            this.tempCelsius = tempC;
        }

        public WeatherCondition getCondition() {
            return condition;
        }

        public void setCondition(WeatherCondition condition) {
            this.condition = condition;
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class WeatherCondition {
        private String text;

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }
    }
}