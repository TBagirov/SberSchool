package org.bagirov.cahce;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TaskExecutor implements DataProcessor, Serializable {

    @Override
    public List<String> executeTask(String parameter, double numericValue, Date moment) throws InterruptedException {
        System.out.println("Запуск длительной операции...");
        Thread.sleep(3000);

        List<String> output = new ArrayList<>();
        output.add("Результат для " + parameter);
        output.add("Значение: " + numericValue);

        return output;
    }
}
