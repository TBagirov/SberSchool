package org.bagirov.model.task2;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
public class UrlContentReader {

    // Метод для чтения содержимого по URL
    public static void readContent(String urlString) {
        try {
            // Создаем объект URL
            URL url = new URL(urlString);

            // Открываем соединение
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            // Проверяем успешный ответ
            if (connection.getResponseCode() != HttpURLConnection.HTTP_BAD_REQUEST) {
                System.out.println("Указан корректный url!");
            }
            else{ System.out.println("Ошибка: Сайт вернул код ответа " + connection.getResponseCode());
            }

        } catch (MalformedURLException e) {
            System.out.println("Неправильный формат URL. Укажите корректный адрес.");
        } catch (Exception e) {
            System.out.println("Ошибка при доступе к ресурсу: " + e.getMessage());
        }
    }
}