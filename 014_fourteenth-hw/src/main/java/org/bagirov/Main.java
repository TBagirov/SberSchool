package org.bagirov;

import org.bagirov.cache.CacheAspect;

import java.lang.reflect.Method;

public class Main {
    public static void main(String[] args) {
        Calculator calculator = new Calculator();
        CacheAspect aspect = new CacheAspect();

        try{
            Method method = Calculator.class.getMethod("fibonachi", int.class);
            System.out.println(aspect.handleCachable(method, new Object[]{10}, calculator));


            System.out.println(aspect.handleCachable(method, new Object[]{10}, calculator)); // берется из кэша

        } catch (Exception e){
            e.printStackTrace();
        }
    }
}