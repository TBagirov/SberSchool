package org.bagirov;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Encryptor {
    public static void encryptFile(String inputFilePath, String outputFilePath, String key) throws IOException {
        byte[] originalBytes = Files.readAllBytes(Path.of(inputFilePath)); // Читаем исходный файл
        byte[] encryptedBytes = new byte[originalBytes.length];
        byte[] keyBytes = key.getBytes();

        for (int i = 0; i < originalBytes.length; i++) {
            encryptedBytes[i] = (byte) (originalBytes[i] ^ keyBytes[i % keyBytes.length]); // XOR для шифрования
        }

        Files.write(Path.of(outputFilePath), encryptedBytes); // Записываем зашифрованный файл
    }

    public static void main(String[] args) throws IOException {
        encryptFile("D:\\Java\\SberSchool\\07_seventh-hw\\src\\main\\java\\org\\bagirov\\Example.class",
                "D:\\Java\\SberSchool\\07_seventh-hw\\encryptClass\\Example.enc",
                "my-secret-key"
        ); // Шифруем файл Example.class
    }
}

