package org.bagirov.task;

import org.bagirov.model.task4.ClassTest;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public class Task4 {

    public static void run() throws IllegalAccessException {
        System.out.println("=============================");

        Field[] declaredFields = ClassTest.class.getDeclaredFields();

        for (Field field : declaredFields) {
            if (field.getType() == String.class) {
                int fieldModifiers = field.getModifiers();

                if (Modifier.isFinal(fieldModifiers) || Modifier.isStatic(fieldModifiers)) {
                    String fieldName = field.getName();
                    String fieldValue = (String) field.get(null); // Получаем значение поля

                    if (fieldName.equals(fieldValue)) {
                        System.out.printf("Строковая константа совпадает по имени (%s) и значению (%s)\n", fieldName, fieldValue);
                    } else {
                        System.out.printf("Строковая константа не совпадает по имени (%s) и значению (%s)\n", fieldName, fieldValue);
                    }
                }
            }
        }
    }
}
