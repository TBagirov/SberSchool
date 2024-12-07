package org.bagirov.model.task1;

public class CalculatorImpl implements Calculator {
    /**
     * Расчет факториала числа.
     * @param number
     */

    @Override
    public int calc(int number) {
        if (number < 0) {
            throw new IllegalArgumentException("Число не может быть отрицательным");
        }

        int result = 1;

        for (int i = 1; i <= number; i++) {
            result *= i;
        }

        return result;
    }
}
