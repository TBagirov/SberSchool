package org.bagirov;

import org.bagirov.cahce.CachingWrapper;
import org.bagirov.cahce.DataProcessor;
import org.bagirov.cahce.TaskExecutor;

import java.util.Date;
import java.util.List;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        CachingWrapper cacheProxy = new CachingWrapper("cache");
        DataProcessor service = cacheProxy.wrap(new TaskExecutor());

        List<String> result1 = service.executeTask("work1", 15, new Date());
        List<String> result2 = service.executeTask("work1", 15, new Date()); // Из кэша

        System.out.println(result1);
        System.out.println(result2);
    }

}