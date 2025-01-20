package org.bagirov;

import org.bagirov.task.Task1;
import org.bagirov.task.Task2;
import org.bagirov.task.TaskStrategy;

import java.util.ArrayList;
import java.util.List;


public class Main {
    public static void main(String[] args) {
        List<TaskStrategy> tasks = new ArrayList<>();

        tasks.add(new Task1());
        tasks.add(new Task2());

        for (TaskStrategy task : tasks) {
            task.execute();
        }
    }
}