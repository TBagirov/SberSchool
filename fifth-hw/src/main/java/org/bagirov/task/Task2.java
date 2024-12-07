package org.bagirov.task;

import org.bagirov.model.task2.ClassChild;

import java.lang.reflect.Method;

public class Task2 {

    public static void run(){
        System.out.println("=============================");

        Class<ClassChild> classReference = ClassChild.class;

        System.out.printf("Методы класса %s: \n", classReference.getName());
        for (Method method : classReference.getDeclaredMethods()) {
            System.out.println(method.getName());
        }

        System.out.println();
        Class<? super ClassChild> parentClass = classReference.getSuperclass();

        System.out.printf("Методы класса %s (родительский класс %s): \n", parentClass.getName(), classReference.getName());
        for (Method method : parentClass.getDeclaredMethods()) {
            System.out.println(method.getName());
        }
    }
}
