package org.bagirov;

import org.bagirov.cache.Cachable;
import org.bagirov.db.H2DB;

import java.util.ArrayList;
import java.util.List;

public class Calculator {
    @Cachable(H2DB.class)
    public List<Integer> fibonachi(int n) throws InterruptedException {
        List<Integer> fib = new ArrayList<>();
        int a = 0, b = 1;
        for (int i = 0; i < n; i++) {
            fib.add(a);
            int next = a + b;
            a = b;
            b = next;
        }

        System.out.println("Усердно считаем...");
        Thread.sleep(5000);

        return fib;
    }
}
