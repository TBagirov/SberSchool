package org.bagirov.task;

import org.bagirov.model.task6.Calculator;
import org.bagirov.model.task6.CalculatorImpl;
import org.bagirov.model.task6.MetricInvocationHandler;

import java.lang.reflect.Proxy;

public class Task6 {

    public static void run(){
        System.out.println("=============================");

        Calculator delegate = new CalculatorImpl();

        Calculator calculator = (Calculator) Proxy.newProxyInstance(
                delegate.getClass().getClassLoader(),
                delegate.getClass().getInterfaces(),
                new MetricInvocationHandler(delegate)
        );

        System.out.println(calculator.calc(4));
        System.out.println(calculator.calc(2));
    }

}
