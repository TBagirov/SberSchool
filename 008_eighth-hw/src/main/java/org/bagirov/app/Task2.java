package org.bagirov.app;

import java.io.IOException;
import java.util.Scanner;

import static org.bagirov.model.task2.WeatherService.*;

public class Task2 {
    public static void run(){
        Scanner scanner = new Scanner(System.in);

        System.out.println("Введите название города:");
        String city = scanner.nextLine();

        try {
            String weatherData = getWeatherData(city);
            WeatherInfo weatherResponse = parseWeatherJson(weatherData);
            printWeatherInfo(weatherResponse);
        } catch (IOException e) {
            System.err.println("Ошибка при получении данных о погоде: " + e.getMessage());
        }
    }
}
