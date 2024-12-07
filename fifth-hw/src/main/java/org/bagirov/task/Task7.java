package org.bagirov.task;

import org.bagirov.model.task7.BeanUtils;
import org.bagirov.model.task7.Source;
import org.bagirov.model.task7.Target;


public class Task7 {

    public static void run(){
        System.out.println("=============================");

        Source sourceObject = new Source();
        sourceObject.setName("Alice");
        sourceObject.setAge(30);

        Target targetObject = new Target();
        targetObject.setName("Default");
        targetObject.setAge(0);

        System.out.println("Перед вызовом BeanUtils:");
        System.out.println("Имя Target: " + targetObject.getName());
        System.out.println("Возраст Target: " + targetObject.getAge());

        BeanUtils.assign(targetObject, sourceObject);

        System.out.println("После вызова BeanUtils:");
        System.out.println("Имя Target: " + targetObject.getName());
        System.out.println("Возраст Target: " + targetObject.getAge());
    }

}
