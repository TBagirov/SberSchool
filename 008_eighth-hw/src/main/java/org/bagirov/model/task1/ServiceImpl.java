package org.bagirov.model.task1;


import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class ServiceImpl implements Service, Serializable {
    @Override
    public List<String> run(String item, double value, Date date) throws InterruptedException {
        System.out.println("Выполнение...");
        Thread.sleep(2000);
        return Arrays.asList("Результат " + item, " с значением: " + value);
    }
}
