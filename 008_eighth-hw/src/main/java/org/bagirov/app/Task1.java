package org.bagirov.app;

import org.bagirov.model.task1.CacheProxy;
import org.bagirov.model.task1.Service;
import org.bagirov.model.task1.ServiceImpl;

import java.util.Date;
import java.util.List;

public class Task1 {
    public static void run() throws InterruptedException {
        CacheProxy cacheProxy = new CacheProxy("cache");
        Service service = cacheProxy.cache(new ServiceImpl());

        List<String> result1 = service.run("work1", 20, new Date());
        List<String> result2 = service.run("work2", 20, new Date());

        System.out.println(result1);
        System.out.println(result2);
    }
}
