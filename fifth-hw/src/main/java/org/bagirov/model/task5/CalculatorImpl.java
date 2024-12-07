package org.bagirov.model.task5;

public class CalculatorImpl implements Calculator {

    @Override
    public int calc(int arg) {
        System.out.println("Вызван calc: " + arg);
        try {
            System.out.printf("Вычисляем (%d * 5), пожалуйста подождите\n", arg);
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return arg * 5;
    }
}
