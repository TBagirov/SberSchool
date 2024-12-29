package org.bagirov.task;

import org.bagirov.model.task1.CalculatorImpl;
import org.bagirov.model.task1.Calculator;

public class Task1 {

    private Calculator calculator;

    public Task1(){this(new CalculatorImpl());}

    public Task1(Calculator calculator){
        this.calculator = calculator;

    }

    public void run(int num){
        System.out.println("=============================");

        System.out.printf("Факториал для числа %d = %d", num, calculator.calc(num));
    }

}
