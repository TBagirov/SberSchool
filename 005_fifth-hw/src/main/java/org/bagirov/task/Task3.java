package org.bagirov.task;

import org.bagirov.model.task3.ClassTest;

import java.lang.reflect.Method;

public class Task3 {
    public static void run(){
        System.out.println("=============================");

        Class<ClassTest> classReference = ClassTest.class;

        System.out.printf("Список всех геттеров класса %s:\n", classReference.getName());

        for (Method method : classReference.getDeclaredMethods()) {
            if (method.getName().startsWith("get")
                    && method.getParameterCount() == 0
                    && method.getReturnType() == String.class) {

                System.out.println(method.getName());
            }
        }
    }
}
