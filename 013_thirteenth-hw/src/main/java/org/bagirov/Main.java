package org.bagirov;

import org.bagirov.cache.CachingWrapper;
import org.bagirov.cache.DataProcessor;
import org.bagirov.cache.TaskExecutor;

import java.util.Date;
import java.util.List;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        CachingWrapper cacheProxy = new CachingWrapper("cache");
        DataProcessor service = cacheProxy.wrap(new TaskExecutor());

        List<String> result1 = service.executeTask("work1", 12, new Date());
        List<String> result2 = service.executeTask("work1", 12, new Date()); // Из кэша

        System.out.println(result1);
        System.out.println(result2);
    }

}