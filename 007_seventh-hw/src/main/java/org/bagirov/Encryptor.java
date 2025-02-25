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

        String pathRead = System.getProperty("user.dir") + "/src/main/java/org/bagirov/Example.class";
        String pathWrite = System.getProperty("user.dir") + "/encryptClass/Example.enc";
        encryptFile(pathRead,
                pathWrite,
                "my-secret-key");

    }
}

