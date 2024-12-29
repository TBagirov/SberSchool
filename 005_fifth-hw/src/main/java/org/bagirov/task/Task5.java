package org.bagirov.task;

import org.bagirov.model.task5.Calculator;
import org.bagirov.model.task5.CalculatorImpl;

import java.time.Duration;
import java.time.LocalDateTime;

public class Task5 {

    private Calculator calculator;

    public Task5(){this(new CalculatorImpl());}

    public Task5(Calculator calculator){
        this.calculator = calculator;

    }


    public void run(){
        System.out.println("=============================");

        LocalDateTime start =  LocalDateTime.now();
        System.out.println(calculator.calc(3));
        System.out.println(calculator.calc(5));
        System.out.println(calculator.calc(7));
        System.out.println(calculator.calc(3));
        System.out.println(calculator.calc(7));
        System.out.println(calculator.calc(5));
        System.out.println(calculator.calc(5));
        System.out.println(calculator.calc(3));
        System.out.println(calculator.calc(7));

        System.out.print("\nВремя выполнение: ");
        System.out.println(Duration.between(start, LocalDateTime.now()));
    }

}
