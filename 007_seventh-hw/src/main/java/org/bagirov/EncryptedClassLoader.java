package org.bagirov;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;


public class EncryptedClassLoader extends ClassLoader {
    private final String key;
    private final File dir;

    public EncryptedClassLoader(String key, File dir, ClassLoader parent) {
        super(parent);
        this.key = key;
        this.dir = dir;
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        File classFile = new File(dir, name.replace('.', File.separatorChar) + ".enc");
        if (!classFile.exists()) {
            throw new ClassNotFoundException("Class not found: " + name);
        }

        try {
            // Читаем зашифрованный файл класса
            byte[] encryptedBytes = Files.readAllBytes(classFile.toPath());

            // Дешифруем данные
            byte[] decryptedBytes = decrypt(encryptedBytes);

            // Создаем класс из байтов
            return defineClass(name, decryptedBytes, 0, decryptedBytes.length);
        } catch (IOException e) {
            throw new ClassNotFoundException("Failed to load class: " + name, e);
        }
    }

    private byte[] decrypt(byte[] encryptedBytes) {
        byte[] decryptedBytes = new byte[encryptedBytes.length];
        byte[] keyBytes = key.getBytes(); // Преобразуем ключ в массив байт

        for (int i = 0; i < encryptedBytes.length; i++) {
            decryptedBytes[i] = (byte) (encryptedBytes[i] ^ keyBytes[i % keyBytes.length]); // XOR
        }

        return decryptedBytes;
    }

    public static void main(String[] args) {
        try {
            File dir = new File("D:\\Java\\SberSchool\\07_seventh-hw\\encryptClass");
            EncryptedClassLoader loader = new EncryptedClassLoader("my-secret-key", dir, ClassLoader.getSystemClassLoader());

            // Загружаем класс
            Class<?> loadedClass = loader.loadClass("org.bagirov.Example");
            System.out.println("Class loaded: " + loadedClass.getName());
            Example plugin = (Example) loadedClass.newInstance();
            plugin.sayHello();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}